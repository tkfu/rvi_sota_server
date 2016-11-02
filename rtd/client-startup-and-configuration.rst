layout: page title: "Client Startup and Configuration" category: cli date: 2016-10-10 15:23:30 order: 2 --- :icons: font

Starting the SOTA client
========================

In general, the SOTA client should be run at startup, as a service. For example, if you’re using systemd you would want a service file like this:

**Example: systemd service file.**

::

    [Unit]
    Description=SOTA Client
    Wants=network-online.target
    After=network.target network-online.target
    Requires=network-online.target

    [Service]
    RestartSec=5
    Restart=on-failure
    Environment="RUST_LOG=info"
    DefaultTimeoutStopSec=5
    ExecStart=/usr/bin/sota_client --config /etc/sota.toml

    [Install]
    WantedBy=multi-user.target

Systemd is not a requirement, of course; you can use whatever startup system you like, or just run directly from the command line to test.

SOTA Client config file guide
=============================

The config file for the SOTA client is in TOML format, and can be invoked at startup with ``sota_client  --config /path/to/config_file.toml``. Example config files for use with `RVI <https://github.com/genivi/rvi_sota_client/raw/master/tests/genivi.sota.toml>`__ or `HTTP <https://github.com/genivi/rvi_sota_client/raw/master/tests/sota.toml>`__ transport are available in the `rvi\_sota\_client <https://github.com/genivi/rvi_sota_client/>`__ repo. If you’re using a commercial service like `ATS Garage <https://app.atsgarage.com>`__, you’ve already been provided with a config file; you can use that as a basis for any changes you need to make.

[auth]
------

This section is required for connection to a SOTA server that implements authentication. It needs to be the first section of the config file. If you are not using authentication, or if you are using RVI transport for authentication, you should **delete** this section entirely.

::

    [auth]
    server = "https://auth-plus.gw.prod01.advancedtelematic.com" 
    client_id = "bf66425f-d4d6-422b-b510-7c7f178af9fe" 
    client_secret = "hr8nEWzQc9" 
    credentials_file = "/opt/sota/credentials.toml" 

-  The URL of the auth server. Example given is from `ATS Garage <https://app.atsgarage.com>`__.

-  A unique client ID and secret for this device.

-  If this value is defined, SOTA client will check this file for auth credentials first, and use the credentials it finds there. If the file does not exist, it will be created with the ``client_id`` and ``client_secret`` above.

[core]
------

This section contains configuration for communicating with the Core server.

::

    [core]
    server = "https://sota-core.gw.prod01.advancedtelematic.com" 
    polling = true 
    polling_sec = 10 

-  The URL of the Core server. Example given is from `ATS Garage <https://app.atsgarage.com>`__.

-  Boolean indicating whether the Core server should be polled for new updates. You should turn this off if you are using RVI, or if there is an external component on your device using the `GetUpdateRequests command <../cli/client-commands-and-events-reference.html#commands>`__.

-  Set the polling frequency in seconds. This will have no effect if polling is off.

[device]
--------

This section contains device-specific configuration.

::

    [device]
    uuid = "123e4567-e89b-12d3-a456-426655440000" 
    system_info = "system_info.sh" 
    packages_dir = "/tmp/" 
    package_manager = "off" 
    certificates_path = "/tmp/sota_certificates" 

-  The UUID of the device. This is usually assigned by the Core server on device creation. With `ATS Garage <https://app.atsgarage.com>`__, this will be provided in the config file available for download.

-  The script to use to gather system information. By default, this uses `system\_info.sh <https://github.com/genivi/rvi_sota_client/blob/master/run/system_info.sh>`__.

-  The location SOTA Client should use for temporary package storage until they are processed by the software loading manager.

-  The software loading manager backend to use. Possible values are ``deb``, ``rpm``, and ``off``. If an external software loading manager is in use, this should be set to ``off``.

-  The certificate authorities SOTA Client trusts. Defaults are taken from Mozilla Servo.

[network]
---------

This section contains network configuration information. Some values may be ignored if the corresponding communication methods aren’t enabled.

::

    [network]
    http_server = "127.0.0.1:8888" 
    socket_commands_path = "/tmp/sota-commands.socket" 
    socket_events_path = "/tmp/sota-events.socket" 
    websocket_server = "127.0.0.1:9081" 
    rvi_edge_server = "127.0.0.1:9080" 

-  The host and port the client should listen on for commands if the http gateway is enabled in the [gateway] section.

-  The name of the unix domain socket to be used for receiving commands, if the socket gateway is enabled in the [gateway] section.

-  The name of the unix domain socket to be used for sending events, if the socket gateway is enabled in the [gateway] section.

-  The host and port to listen for local websocket connections on. Could be used, for example, for integration with an HMI.

-  The host and port the client should listen for RVI messages on, if the rvi gateway is enabled in the [gateway] section.

[gateway]
---------

The SOTA Client communicates with the device’s software loading manager (or other interested parties) through various gateways. For more details on how this works, please see the `Client commands and events API reference <../cli/client-commands-and-events-reference.html>`__.

::

    [gateway]
    console = false 
    dbus = false
    http = false 
    rvi = false
    socket = false 
    websocket = true

-  REPL mode, for debug use only.

-  Simple http server, for sending commands to the client remotely. Note that this does *not* affect how the client communicates with the server.

-  Unix domain sockets for local communication.

Optional gateway: [rvi]
~~~~~~~~~~~~~~~~~~~~~~~

Remote Vehicle Interaction (RVI) is an open source infrastructure developed by GENIVI and Jaguar Land Rover to power the next generation of connected vehicle services. This section contains values for configuration of RVI nodes. Note that having this section defined does not imply that RVI will be used; if the RVI gateway is turned off in the ``[gateway]`` section, this is ignored.

::

    [rvi]
    client = "http://127.0.0.1:8901"
    storage_dir = "/var/sota"
    timeout = 20

Optional gateway: [dbus]
~~~~~~~~~~~~~~~~~~~~~~~~

This section contains values for dbus configuration, using the GENIVI software loading manager’s names as the default. Note that having this section defined does not imply that dbus will be used; if the dbus gateway is turned off in the ``[gateway]`` section, this is ignored.

::

    [dbus]
    name = "org.genivi.SotaClient"
    path = "/org/genivi/SotaClient"
    interface = "org.genivi.SotaClient"
    software_manager = "org.genivi.SoftwareLoadingManager"
    software_manager_path = "/org/genivi/SoftwareLoadingManager"
    timeout = 60
