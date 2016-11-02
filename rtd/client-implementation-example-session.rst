layout: page title: "RVI Client Implementation Example Session" category: cli date: 2015-10-22 18:26:21 order: 8 --- :icons: font

This page takes a look at a typical RVI client session, where the client connects to the RVI node, gets notified of an available update, downloads and installs the update, and reports the success back to the server. We’ll break this down in detail, and look at each of the steps of the process. You can also download `the debug log file from this session </rvi_sota_server/example-client-session.log>`__.

1. Registering services

   a. Client connects to RVI node.

   b. RVI node lists the services it has available.

   c. Client registers its available services with the RVI node.

   d. Client gets the fully qualified names of its services from the node in return.

2. Package notification and download

   a. Client receives a notification from the server that a package update is available.

   b. Client tells the server it can start sending the package.

   c. Server tells the client the package’s SHA1 checksum, and the total number of 64kb chunks in the package, and the client acknowledges.

   d. Server starts sending chunks, and the client acknowledges each one.

   e. Once the client has acknowledged all the chunks, the server tells the client the transfer is finished.

3. Package installation and report

   a. The client assembles the chunks, and calls the local software loading manager to install the package.

   b. Once the SLM terminates, the client sends a report back to the server with the success or failure of the package install.

1. Registering Services
=======================

When the client connects to the RVI node, the first thing that needs to happen is an exchange of information about services: the client registers with the RVI node all of the services it implements, and the RVI node tells the client which services it has available. The RVI node also gives the client a unique name for each service it registers.

Here is how the RVI node notifies the client about an available service:

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 1,
      "method": "services_available",
      "params": {
        "services": [
          "genivi.org/backend/sota/start"
        ]
      }
    }

    **Tip**

    Currently, the services the RVI node will make available are ``genivi.org/backend/sota/start``, ``genivi.org/backend/sota/ack``, ``genivi.org/backend/sota/report``, and ``genivi.org/backend/sota/packages``.

Here is how the client registers a service with the RVI node:

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 2649007917144,
      "method": "register_service",
      "params": {
        "network_address": "sota_client_1:8090", 
        "service": "/sota/notify"
      }
    }

-  This session was running in docker containers, with ``sota_client_1`` as the hostname of the client container. It would be more common to supply an ip address.

    **Tip**

    The client registers 5 services with RVI: ``/sota/start``, ``/sota/chunk``, ``/sota/finish``, ``/sota/getpackages``, and ``/sota/abort``.

The RVI node responds with a status code and the *fully qualified* name of the service:

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 2649007917144,
      "result": {
        "status": 0,
        "service": "genivi.org/vin/VINOOLAM0FAU2DEEP/sota/notify"
      }
    }

It’s important for the client to save each service name—​it will be sent with future requests.

2. Package notification and download
====================================

When an update is ready to install, the server will send a message to the client’s ``/sota/notify`` service. Let’s take a look at the message, and explain the elements of it:

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 1,
      "method": "message",
      "params": {
        "service_name": "/sota/notify",
        "parameters": [ 
          {
            "services": { 
              "ack": "genivi.org/backend/sota/ack",
              "report": "genivi.org/backend/sota/report",
              "start": "genivi.org/backend/sota/start",
              "packages": "genivi.org/backend/sota/packages"
            },
            "packages": [ 
              {
                "size": 35768,
                "package": {
                  "version": "7.6.2",
                  "name": "ghc"
                }
              }
            ]
          }
        ]
      }
    }

-  Note that the parameters are wrapped as a single-element array. This is a workaround for a bug in the current version of RVI, and will likely be removed in subsequent releases.

-  Every ``/sota/notify`` message will include a list of fully qualified names of the sender’s available services.

-  This is the real content of the notification: the names, versions, and sizes of the packages to be updated.

Once the client is ready to receive the package (for example, when the user accepts the update), it sends a message to the RVI node’s ``genivi.org/backend/sota/start`` service.

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 2677667615071,
      "method": "message",
      "params": {
        "service_name": "genivi.org/backend/sota/start",
        "timeout": 1445520390,
        "parameters": [
          {
            "packages": [ 
              {
                "name": "ghc",
                "version": "7.6.2"
              }
            ],
            "services": { 
              "start": "genivi.org/vin/VINOOLAM0FAU2DEEP/sota/start",
              "chunk": "genivi.org/vin/VINOOLAM0FAU2DEEP/sota/chunk",
              "abort": "genivi.org/vin/VINOOLAM0FAU2DEEP/sota/abort",
              "finish": "genivi.org/vin/VINOOLAM0FAU2DEEP/sota/finish",
              "getpackages": "genivi.org/vin/VINOOLAM0FAU2DEEP/sota/getpackages"
            },
            "vin": "VINOOLAM0FAU2DEEP"
          }
        ]
      }
    }

-  A list of the packages the client is ready to accept. This needs to be a subset of the packages that the client has been notified about.

-  This is an important requirement to remember: Every time a client sends a ``/sota/start`` message, it has to include the fully qualified names of all of its services, except for notify. (Notify isn’t required because ``start`` is a response to ``notify``, so the RVI node must already know about it.)

Now, the RVI node sends a message to the client’s ``/sota/start`` service for each package the client has accepted, giving an SHA1 checksum and the number of chunks that will be sent.

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 1,
      "method": "message",
      "params": {
        "service_name": "/sota/start",
        "parameters": [
          {
            "chunkscount": 1, 
            "checksum": "e6db09bd2c84db66534107da4ef00e6addccba8e", 
            "package": {
              "version": "7.6.2",
              "name": "ghc"
            }
          }
        ]
      }
    }

-  The package file will be sent in 64kb chunks; this tells the client how many chunks there are in total.

-  The SHA1 checksum of the final, reassembled file.

In response, the client must send an ack message:

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 2677989151999,
      "method": "message",
      "params": {
        "service_name": "genivi.org/backend/sota/ack",
        "timeout": 1445520390, 
        "parameters": [
          {
            "package": {
              "name": "ghc",
              "version": "7.6.2"
            },
            "chunks": [], 
            "vin": "VINOOLAM0FAU2DEEP"
          }
        ]
      }
    }

-  The timeout is given as a Unix epoch time.

-  This is an array with the list of all the chunks the client already has.

Once the server receives the ack, it will start sending chunks.

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 1,
      "method": "message",
      "params": {
        "service_name": "/sota/chunk",
        "parameters": [
          {
            "index": 1,
            "bytes": "7avu2wMAA<rest of base64 encoded data chunk omitted>AAAKWVo=",
            "package": {
              "version": "7.6.2",
              "name": "ghc"
            }
          }
        ]
      }
    }

The client sends back an ack each time it receives a chunk; the format is the same as above. Once the server has received an ack for every chunk, it will send a finish message:

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 1,
      "method": "message",
      "params": {
        "service_name": "/sota/finish",
        "parameters": [
          {
            "package": {
              "version": "7.6.2",
              "name": "ghc"
            }
          }
        ]
      }
    }

3. Package installation and report
==================================

Now that the client has received all the chunks, it needs to reassemble them into the binary file, and install the package using the system’s software loading manager. Once it gets the results of the install, it sends a report message back to the server:

.. code:: json

    {
      "jsonrpc": "2.0",
      "id": 2680479224662,
      "method": "message",
      "params": {
        "service_name": "genivi.org/backend/sota/report",
        "timeout": 1445520392,
        "parameters": [
          {
            "package": {
              "name": "ghc",
              "version": "7.6.2"
            },
            "status": true, 
            "description": "Successfully installed package", 
            "vin": "VINOOLAM0FAU2DEEP"
          }
        ]
      }
    }

-  A boolean. True for success, false for anything else.

-  A descriptive status message. Most probably, it’s easiest to pass the software loading manager’s status message on directly.
