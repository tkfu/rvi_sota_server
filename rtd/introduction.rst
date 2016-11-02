layout: page title: "Introduction" category: cli date: 2016-10-10 15:27:15 order: 1 ---

Developing a SOTA client
========================

A SOTA server isn’t very useful unless it has clients to talk to. We provide a `reference implementation of a client <https://github.com/genivi/rvi_sota_client>`__, but you’re also free to implement your own client. If you want to develop an RVI-based client, the reference documentation you’re looking for is over `here <../dev/client-implementation.html>`__, or if you’re going to communicate with Core’s API endpoints directly you should look at the `Core API Documentation <../dev/api.html>`__.

Understanding the reference client
==================================

The SOTA client is, in a nutshell, a middleman. It receives update notifications from Core, and downloads the update packages. It then passes them to a software loading manager for installation. (For a reference implementation of a software loading manager, see `the GENIVI SWM <https://github.com/GENIVI/genivi_swm>`__). Once the software loading manager is finished, it sends a report on the install, which the SOTA client passes back to Core.

It does all this through a command/event infrastructure: it broadcasts events, and listens for commands. For more detail, see the `SOTA client command and event reference <../cli/client-commands-and-events-reference.html>`__.

A flow for the installation of a single update might look like this (HTTP transport, RVI disabled):

Client polls core for update → Event: UpdatesReceived → Client downloads update → Event: DownloadComplete → Software manager installs update → SWM sends command to client: SendUpdateReport → Client reports the status of the install back to the server

The reference client can communicate with SOTA Core in two ways, configurable on client startup. If you want to use RVI nodes, you can enable RVI communication and disable HTTP communication. If you prefer to let the client communicate with SOTA Core directly over HTTP/HTTPS, it can also do that. See `Client Startup and Configuration <../cli/client-startup-and-configuration.html>`__ for detailed configuration information.
