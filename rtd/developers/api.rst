API
```

SOTA consists of three independent microservices: SOTA Core, SOTA Resolver, and SOTA Device Registry. Core has all of the functions for creating update campaigns and managing packages and package binaries. Resolver maintains a database storing vehicles, packages (but not package binaries), hardware components, filters, packages, and the associations between those things. Device Registry manages the creation of devices, and assigns each one a UUID.

The complete API spec is available in `OpenAPI 2.0 <https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md>`__ format. You can view the spec files yourself in JSON or YAML formats, or view it on the web in SwaggerUI.

Core
====

`SwaggerUI <../swagger/sota-core.html#!/default>`__ `YAML <../swagger/sota-core.yml>`__ `JSON <../swagger/sota-core.json>`__

Resolver
========

`SwaggerUI <../swagger/sota-resolver.html#!/default>`__ `YAML <../swagger/sota-resolver.yml>`__ `JSON <../swagger/sota-resolver.json>`__

Device Registry
===============

`SwaggerUI <../swagger/sota-device_registry.html#!/default>`__ `YAML <../swagger/sota-device_registry.yml>`__ `JSON <../swagger/sota-device_registry.json>`__
