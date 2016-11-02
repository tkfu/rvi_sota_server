SOTA Architecture
`````````````````

SOTA employs a microservice architecture, allowing piecemeal replacement of parts, depending on your project’s needs. Here we provide a brief explanation of each of the microservices that comprise SOTA, and how they fit together. Each microservice exposes its functionality through a REST API.

SOTA Core is what actually handles the distribution of software updates to vehicles. It manages per-vehicle download queues, creation and persistent storage of the state of OTA update campaigns, and distribution of software packages to vehicles.

SOTA Device Registry is a required component. While the original specification for SOTA called for clients to always be vehicles uniquely identified by VINs, there are automotive applications that don’t necessarily want to use VINs as mandatory primary keys. The device registry keeps track of the client devices, and assigns each registered device a UUID, with VIN being an optional secondary identifier. Core creates update campaigns to vehicles/devices identified by their UUIDs, and so to create a campaign from a list of VINs (or other secondary identifiers) and corresponding software packages, it is necessary to query the device registry to resolve the UUID from the VIN and namespace.

SOTA Resolver is an optional component. Its primary purpose is, when given a packageID, to return a list of the devices that need that package. This allows the building of update campaigns. To accomplish that task, Resolver keeps an inventory of software packages and device characteristics (like, for example, hardware components/ECUs available or installed software versions). It also implements a filter engine to select device by characteristic, and permits the association of filters with packages. Thus, when Resolver is asked to resolve a packageID, it checks which filters are associated with the package, runs the filters against the device inventory, and returns a list of matching devices so that Core can create a properly-targeted update.

Finally, SOTA contains a bare-bones admin interface. This web-based HMI exposes all of the functionality of the APIs of each component, and can be studied as an example of how to implement a more full-featured interface, or how to interact directly with the APIs. It also forwards API calls with valid session cookies on to the other components.

In summary, the system looks a bit like this:

|System Architecture Diagram|

RVI clients would interact directly with Core’s RVI node (note: needs to be deployed separately), while HTTPS clients interact with Core directly. For the moment, however, setting up authentication is out of scope for this documentation; for more information we suggest `looking at the code directly <https://github.com/genivi/rvi_sota_server/tree/master/common/src/main/scala/org/genivi/sota/http>`__.

An ER diagram of the databases for core, resolver, and device registry can be found `here <../images/ER-Diagram.svg>`__.

Context Diagrams
================

RVI SOTA is designed with security in mind. You can read a lot more about our security processes on the `contributing <../doc/contributing.html>`__, `whitelisted interactions <../sec/whitelisted-interactions.html>`__, and `security threats/mitigations <../sec/security-threats-mitigations.html>`__ pages, but for a broad view of how the architectural design of the project helps keep it secure, you can take a look at these context diagrams.

Level 0
-------

|Level 0 Context Diagram|

Level 1 SOTA Server
-------------------

|Level 1 Context Diagram|

Requirements
============

You can find a complete list of the `software requirements here <../ref/requirements.html>`__

Dependencies
============

You can find a complete list of `software dependencies here <../ref/dependencies.html>`__

.. |System Architecture Diagram| image:: ../images/System-Architecture-Diagram.svg
.. |Level 0 Context Diagram| image:: ../images/Level-0-Context-Diagram.svg
.. |Level 1 Context Diagram| image:: ../images/Level-1-SOTA-Server-Context-Diagram.svg

