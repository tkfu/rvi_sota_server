Deployment with ``docker-compose``
``````````````````````````````````

Docker is a great way to get SOTA running in a clean, reproducible way. ``sbt`` can build and publish local docker images to test with, and docker-compose combined with some ATS-provided images makes sure all of SOTA’s dependencies are taken care of.

docker-compose overview
=======================

Deploying with docker-compose should be quite straightforward. Assuming docker is running on the deployment system, ``docker-compose -p sota -f deploy/docker-compose/[filename].yml up`` should get you a running SOTA system. This depends on the following images:

-  From SOTA

   -  advancedtelematic/sota-core

   -  advancedtelematic/sota-resolver

   -  advancedtelematic/sota-webserver

-  External images provided by ATS

   -  advancedtelematic/mariadb

   -  advancedtelematic/rvi (optional)

When you run docker-compose, it will first search for those images locally, and if it does not find them, it will pull the ones ATS has published on Docker Hub. To ensure you’re running the latest code, you can build the images locally with ``sbt docker:publishLocal``. To check for updates on the published images, you can do a ``docker-compose -f deploy/docker-compose/[filename].yml pull``, or simply ``docker pull [image name]`` to update an individual image.

Deployment options
==================

There are three docker-compose files included in the git repo, under ``/deploy/docker-compose``:

-  ``docker-compose.yml`` contains the default SOTA Server components.

-  ``core-rvi.yml`` contains the additional RVI server node with environment overrides for the SOTA Core server.

-  ``client-rvi.yml`` contains the additional RVI client node for testing with the SOTA Client

To start the base SOTA Server, run ``docker-compose -f docker-compose.yml up``. To start the SOTA Server with an RVI backend node, run ``docker-compose -f docker-compose.yml -f core-rvi.yml up``. To start the SOTA Server with both RVI backend and device nodes, run ``docker-compose -f docker-compose.yml -f core-rvi.yml -f client-rvi.yml up``.

Cloud deployment
================

If you want to deploy to the cloud, docker-compose can also help you do that. The easiest way is to use `docker-machine’s AWS driver <https://docs.docker.com/machine/drivers/aws/>`__ to create and deploy to a remote host.

Connecting a client
===================

Once you have the server up and running, you’ll probably want to set up a client and get it talking to the server. See `Building the SOTA Client <../cli/building-the-sota-client.html>`__ for build instructions.

Once you have a binary on a client device, you will need to create a device in the SOTA server UI, and then configure the client to use the device ID you just created. Creating the device is straightforward; log into the admin UI (user: genivi, password: genivirocks!), click the Vehicles tab, and add a new one.

To configure the client to use this UUID, please take a look at the detailed configuration instructions available `here <../cli/client-startup-and-configuration.html>`__.

Connecting a client with RVI
----------------------------

If you want RVI communication, you’ll also need to configure your client RVI node with the appropriate device ID. Edit the **DEVICE\_ID** environment variable in client-rvi.yml to match the device you just created, then restart the client RVI node.

GENIVI Software Loading Manager
-------------------------------

See `genivi\_swm <https://github.com/GENIVI/genivi_swm>`__ on how to run the Software Loading Manager demo. It also contains instructions for creating an update image, which can be uploaded as a package to the SOTA Server.

Now you can create an update campaign on the SOTA Server, using the same update\_id as the uuid in the update image you created. Also, as the genivi\_swm demo runs as root, remember to run the ``sota_client`` as root as well so that they can communicate on the same system bus.

Quickstart
~~~~~~~~~~

::

        git clone https://github.com/GENIVI/genivi_swm
        cd genivi_swm
        export PYTHONPATH="${PWD}/common/"
        python software_loading_manager/software_loading_manager.py

As the genivi\_swm demo runs as root, remember to run the ``sota_client`` as root as well so that they can communicate on the same system bus.
