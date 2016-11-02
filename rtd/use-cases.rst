layout: page title: "Use Cases" category: ref date: 2015-08-26 14:13:12 order: 5 --- :icons: font :toc: macro

SOTA Server Configuration
=========================

`VIN\_ADD <#VIN_ADD>`__ Add a VIN to the system
-----------------------------------------------

-  Actors

   -  Web Server

   -  SOTA Server

   -  Resolver

-  Preconditions

   -  VIN does not already exist

-  Steps

   -  E1 - An ADD\_VIN command is sent from Web Server to SOTA Server

   -  E2 - VIN is added to the SOTA Server Database

   -  E3 - A success code is sent back to Web Server

   -  E4 - An ADD\_VIN command is snet to External Resolver from Web Server

   -  E5 - VIN is added to External Resolver database

   -  E6 - A success code is sent back to Web Server

-  Postconditions

   -  VIN is part of the system

-  Exceptions

   -  X1 - VIN already exists. Triggered by E2, E5

`VIN\_DEL <#VIN_DEL>`__ Delete an existing VIN
----------------------------------------------

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  VIN has been installed by `VIN\_ADD <#VIN_ADD>`__

-  Steps

   -  E1 - A delete VIN command is sent from Web Server to SOTA Server

   -  E2 - All references to Packages being installed on given VIN are removed from SOTA Server Database

   -  E3 - The VIN is removed from the SOTA Server Database

   -  E4 - Any Data Plan references to the VIN are removed from the SOTA Server Database

   -  E5 - A success code is sent back to the Web Server

   -  E6 - A DELETE\_VIN command is sent from Web Server to Resolver

   -  E7 - All references to Components being installed on the given VIN are removed from the Resolver Database

   -  E8 - All references to Packages being installed on the given VIN are removed from the Resolver Database

   -  E9 - The VIN is removed from the Resolver Database

   -  E10 - A success code is sent back to the Web Server

-  Exceptions

   -  X1 - VIN does not exist. Triggered by E6

`VIN\_SEARCH <#VIN_SEARCH>`__ Search for VINs
---------------------------------------------

Searches and retrieves one or more VINs with their Packages and Components

-  Actors

   -  Web Server

   -  SOTA Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A SEARCH\_VIN command is sent from Web Server to SOTA Server. VIN is searched for using POSIX-style regular expressions.

   -  E2 - The SOTA Server Database is searched for all VINs matching the given expression.

   -  E3 - For each retrieved VIN, the part numbers of all installed Components are retrieved by the Web Server from the Resolver(!)

   -  E4 - For each retrieved VIN, the IDs of all Installed Packages are retrieved

   -  E5 - All matching VINs, with their retrieved Components and Installed Packages are returned by SOTA Server to Web Server

-  Exceptions

   -  None

`VIN\_SET\_DATA\_PLAN <#VIN_SET_DATA_PLAN>`__ Associate Data Plan with VIN
--------------------------------------------------------------------------

Associates a previously created data plan with a given VIN

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  None

-  Steps

   -  E1 - A SET\_VIN\_DATA\_PLAN command is sent from Web Server to SOTA Server with VIN and Data Plan ID.

   -  E2 - The VIN is retrieved from the SOTA Server Database

   -  E3 - The Data Plan is retrieved from the SOTA Server Database

   -  E4 - A success code is sent back to the Web Server

-  Exceptions

   -  X1 - VIN does not exist. Triggered by E2

   -  X2 - Data Plan does not exist. Triggered by E3

`VIN\_GET\_UPDATE\_HISTORY <#VIN_GET_UPDATE_HISTORY>`__ Get installation history for VIN
----------------------------------------------------------------------------------------

All install requests, failed, pending, in-flight or completed are returned.

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  None

-  Steps

   -  E1 - A GET\_VIN\_PACKAGE\_HISTORY command is sent from Web Server to SOTA Server with a VIN.

   -  E2 - The provided VIN is retrieved from the SOTA Server Database

   -  E3 - All updates, completed, failed, in-flight or pending targeting the provided VIN are retieved from the SOTA Server Database, together with the IDs of all Packages included in the update for each VIN

   -  E4 - A success code is sent back to the Web Server, with all updates, their package IDs, their status, and the completion / failure date

-  Exceptions

   -  X1 - VIN does not exist. Triggered by E2

`PACKAGE\_ADD\_SOTASERVER <#PACKAGE_ADD_SOTASERVER>`__ Register a new Package with SOTA Server
----------------------------------------------------------------------------------------------

Add a software Package that can be pushed to a specific Component on a VIN

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  Software package does not already exist

-  Steps

   -  E1 - An ADD\_SOFTWARE\_PACKAGE command is sent from Web Server to SOTA Server together with an ID string, a version (major.minor.patch), a description, and a vendor. The software package binary is sent as part of the command together with a checksum.

   -  E2 - Software package’s meta-data is added to SOTA Server database

   -  E3 - The SOTA Server stores the package binary in its storage area and stores the URL to the binary in the database.

   -  E4 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - Software Package with same ID String and Version is already registered with SOTA Server. Triggered by E2

`PACKAGE\_ADD\_RESOLVER <#PACKAGE_ADD_RESOLVER>`__ Register a new Package with External Resolver
------------------------------------------------------------------------------------------------

Add a software Package that can be pushed to a specific Component on a VIN

-  Actors

   -  Web Server

   -  External Resoler

-  Preconditions

   -  Software package does not already exist

-  Steps

   -  E1 - An ADD\_SOFTWARE\_PACKAGE command is sent from Web Server to the Resolver together with an ID string, a version (major.minor.patch), a description, and a vendor.

   -  E2 - Software package’s meta-data is added to the Resolver database

   -  E3 - An ADD\_SOFTWARE\_PACKAGE command is sent from Web Server to External Resolver together with an ID string.

   -  E4 - Software Package is added to External Resolver database

   -  E5 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - Software Package with same ID String and Version is already registered with SOTA Server. Triggered by E2

`PACKAGE\_SEARCH <#PACKAGE_SEARCH>`__ Search for a Package
----------------------------------------------------------

Searches and retrieves data for software Packages from the system

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  None

-  Steps

   -  E1 - A SEARCH\_PACKAGE command is sent from Web Server to SOTA Server with a regular expression formatted Package ID and version string

   -  E2 - The SOTA Server Database is searched for all Packages matching the search criteria.

   -  E3 - All matching Package IDs, with their version, vendor and descriptions are returned.

-  Exceptions

   -  None

`PLAN\_ADD <#PLAN_ADD>`__ Register a new Data Plan
--------------------------------------------------

Add a Data Plan that can later be used by VINs. Billing Cycles are added to the Data Plan by `PLAN\_ADD\_BILLING\_CYCLE <#PLAN_ADD_BILLING_CYCLE>`__

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  None

-  Steps

   -  E1 - An ADD\_DATA\_PLAN command is sent from Web Server to SOTA Server with a Data Plan ID.

   -  E2 - The Data Plan is added to SOTA Server Database.

   -  E3 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - Data Plan already exists. Triggered by E2.

`PLAN\_ADD\_BILLING\_CYCLE <#PLAN_ADD_BILLING_CYCLE>`__ Associate a Billing Cycle with a Data Plan
--------------------------------------------------------------------------------------------------

Add a billing cycle to a Data Plan previously created with `PLAN\_ADD <#PLAN_ADD>`__.

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  `PLAN\_ADD <#PLAN_ADD>`__ executed to provide a Data Plan to which to add a Billing Cycle

-  Steps

   -  E1 - An ADD\_BILLING\_CYCLE command is sent from Web Server to SOTA Server with a Data Plan ID, a Billing Cycle start date / time, and a Billing Cycle pool size.

   -  E2 - The Data Plan is retrieved from the SOTA Server Database

   -  E3 - A Billing Cycle is created in the SOTA Server Database with zero bytes transmitted, the given start data, and the pool size.

   -  E4 - A success code is returned by SOTA Server to Web Server

-  Exceptions

   -  X1 - Data Plan does not exist. Triggered by E2.

`PLAN\_SEARCH <#PLAN_SEARCH>`__ Find existing Data Plan
-------------------------------------------------------

Search for a Data Plan previously added with PLAN\_ADD.

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  None

-  Steps

   -  E1 - A SEARCH\_DATA\_PLAN command is sent from Web Server to SOTA Server with a Data Plan ID regular expression

   -  E2 - A success code is returned by SOTA Server to Web Server with all located Data Plan IDs

-  Exceptions

   -  None

`PLAN\_GET\_BILLING\_CYCLES <#PLAN_GET_BILLING_CYCLES>`__ Get Billing Cycles for a Data Plan
--------------------------------------------------------------------------------------------

Retrieve billing cycles and their details belonging to a specific Data Plan.

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  None

-  Steps

   -  E1 - A SEARCH\_BILLING\_CYCLES command is sent from Web Server to SOTA Server with a Data Plan ID, an earliest date / time, and a latest date / time.

   -  E2 - The Data Plan is retrieved from SOTA Server Database.

   -  E3 - All Billing Cycles belonging to the Data Plan, with a start date / time between the provided earliest and latest date / time, are retrieved from SOTA Server Database together with their pool size, and used data.

   -  E4 - A success code is returned by SOTA Server to Web Server with all located Billing Cycles, their pool size and data usage.

-  Exceptions

   -  X1 - Data Plan does not exist. Triggered by E2.

`PLAN\_ADD\_TRAFFIC <#PLAN_ADD_TRAFFIC>`__ Add Traffic Information to active Billing Cycle for Data Plan associated with VIN
----------------------------------------------------------------------------------------------------------------------------

Add traffic information to the active Billing Cycle under the Data Plan associated with a specific VIN.

-  Actors

   -  SOTA Server

   -  Network Monitoring Process

-  Preconditions

   -  None

-  Steps

   -  E1 - An ADD\_DATA\_TRAFFIC command is sent from an internal SOTA Server Network Monitoring Process to SOTA Server with a VIN and a byte count of transmitted data.

   -  E2 - The VIN is retrieved from SOTA Server Database.

   -  E3 - The Data Plan setup for the VIN created through the `VIN\_SET\_DATA\_PLAN <#VIN_SET_DATA_PLAN>`__ use case is retrieved from SOTA Server.

   -  E4 - The Billing Cycle, owned by the Data Plan, that has the latest start date / time before the provided date / time stamp is retrieve from the SOTA Server Database.

   -  E5 - The data usage for the given Billing Cycle is incremented by the byte count provided

   -  E6 - A success code is returned to the Network Monitoring Process, together with the retrieved Data Plan ID, and the start date, pool size, and update data usage of the located Billing Cycle.

-  Exceptions

   -  X1 - VIN does not exist. An error code is sent back to Web Server. Triggered by E2.

   -  A1 - No Data Plan is setup for VIN. A success code is sent back to Web Server. Triggered by E3.

   -  A2 - No Billing Cycles have been added to the Data Plan. A success code is sent back to Web Server. Triggered by E4.

External Resolver Configuration
===============================

`FILTER\_ADD <#FILTER_ADD>`__ Add a new Filter
----------------------------------------------

Add a filter

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A FILTER\_ADD command is sent from Web Server to External Resolver with the filter expression and a filter label.

   -  E2 - The filter expression is validated for semantic and syntactic correctness.

   -  E3 - The fitler is stored in the External Resolver Database.

   -  E4 - A success code is returned by External Resolver to Web Server.

-  Exceptions

   -  X1 - Filter Label already exists. Triggered by E1.

   -  X2 - Filter Expression validation fails. Triggered by E2.

`FILTER\_SEARCH <#FILTER_SEARCH>`__ Search for a Filter
-------------------------------------------------------

Search for an existing filter

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A FILTER\_SEARCH command is sent from Web Server to External Resolver with a regular expression describing zero or more filter labels.

   -  E2 - The filters with matching filter labels are retrieved from the External Resolver Database.

   -  E3 - A success code is returned by External Resolver to Web Server, with all matching filter labels and their filter expression.

-  Exceptions

   -  None

`FILTER\_VALIDATE <#FILTER_VALIDATE>`__ Test validity of a Filter Expression
----------------------------------------------------------------------------

Validate filter syntax and semantics

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A FILTER\_VALIDATE command is sent from Web Server to External Resolver with a filter expression.

   -  E2 - The filter expression is validated for semantic and syntactic correctness.

   -  E4 - If the filter expression is valid, a success code is returned to Web Server

   -  E5 - If the filter expression is not valid, an error code is returned together with an error message describing the problem with the filter expression.

-  Exceptions

   -  None

`FILTER\_DELETE <#FILTER_DELETE>`__ Delete a Filter
---------------------------------------------------

Delete an existing filter

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  Filter has been added to External Resolver Database using `FILTER\_ADD <#FILTER_ADD>`__

-  Steps

   -  E1 - A DELETE\_FILTER command is sent from Web Server to External Resolver with a filter label.

   -  E2 - The filter is deleted from the External Resolver Database.

   -  E3 - A success code is returned by External Resolver to Web Server.

-  Exceptions

   -  X1 - Filter label does not exist. Triggered by E2.

`FILTER\_PACKAGE\_ADD <#FILTER_PACKAGE_ADD>`__ Associate a Package with a Filter
--------------------------------------------------------------------------------

Associate an existing filter with an existing Package.

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  Filter has been added to External Resolver Database using `FILTER\_ADD <#FILTER_ADD>`__

   -  Package has been added to External Resolver Database using `PACKAGE\_ADD <#PACKAGE_ADD>`__

-  Steps

   -  E1 - A FILTER\_PACKAGE\_ADD command is sent from Web Server to External Resolver with a filter label and a Package ID.

   -  E2 - The filter is retrieved from External Resolver Database.

   -  E3 - The Package is retrieved from External Resolver Database.

   -  E4 - A reference is added to External Resolver Database that the filter should be applied to all VINs when the Package is to be resolved in `QUEUE\_REQUEST\_ADD <#QUEUE_REQUEST_ADD>`__.

   -  E3 - A success code is returned by External Resolver to Web Server.

-  Exceptions

   -  X1 - Filter label does not exist. Triggered by E2.

   -  X2 - Package ID does not exist. Triggered by E3.

`FILTER\_PACKAGE\_DELETE <#FILTER_PACKAGE_DELETE>`__ Remove a Package from a Filter
-----------------------------------------------------------------------------------

Remove an association between an existing Package and an existing Filter

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  Filter-Package association has been added to External Resolver Database using `FILTER\_PACKAGE\_ADD <#FILTER_PACKAGE_ADD>`__

-  Steps

   -  E1 - A FILTER\_PACKAGE\_DELETE command is sent from Web Server to External Resolver with a filter label and a Package ID.

   -  E2 - The reference that the given Filter should be applied to the specific Package ID is removed from the External Resolver Database.

   -  E3 - A success code is returned by External Resolver to Web Server.

-  Exceptions

   -  X1 - Filter-Package association does not exist. Triggered by E2.

`FILTER\_SEARCH\_PACKAGE\_BY\_FILTER <#FILTER_SEARCH_PACKAGE_BY_FILTER>`__ Get list of Packages by Filter
---------------------------------------------------------------------------------------------------------

Retrieve all Packages associated with a Filter.

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A FILTER\_SEARCH\_PACKAGE\_BY\_FILTER command is sent from Web Server to External Resolver with a filter label.

   -  E2 - The External Resolver Database is searched for all Packages associated with the given Filter.

   -  E3 - A success code is returned by External Resolver to Web Server, with all retrieved Package IDs.

-  Exceptions

   -  X1 - Filter label does not exist. Triggered by E2.

`FILTER\_SEARCH\_FILTER\_BY\_PACKAGE <#FILTER_SEARCH_FILTER_BY_PACKAGE>`__ Get list of Filters by Package
---------------------------------------------------------------------------------------------------------

Retrieve all Filters associated with a Package.

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A FILTER\_SEARCH\_FILTER\_BY\_PACKAGE command is sent from Web Server to External Resolver with a Package ID.

   -  E2 - The External Resolver Database is searched for all Filters assocaited with the given Package.

   -  E3 - A success code is returned by External Resolver to Web Server, with all retrieved Filter Labels.

-  Exceptions

   -  X1 - Package does not exist. Triggered by E2.

`COMP\_ADD <#COMP_ADD>`__ Add a Component
-----------------------------------------

Adds a component that can subsequently be associated with one or more VINs

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - An ADD\_COMPONENT command is sent from Web Server to External Resolver

   -  E2 - The Component is added to External Resolver Database

   -  E3 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - If Component exists, X1 is executed. Triggered at E1.

`VIN\_ADD\_COMP <#VIN_ADD_COMP>`__ Associate a Component with a VIN
-------------------------------------------------------------------

Associates a previously configured Component with a VIN, indicating that the VIN has the given part number installed

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - An ADD\_COMPONENT command is sent from Web Server to External Resolver

   -  E2 - The provided VIN is retrieved from External Resolver Database

   -  E3 - The provided Component is retrieved from External Resolver Database

   -  E4 - The part provided Component is marked as installed on the VIN in External Resolver Database

   -  E5 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - VIN does not exist - an error code is sent back to Web Server. Triggered at E2 if VIN does not exist.

   -  X2 - Component does not exist - an error code is sent back to Web Server. Triggered at E3 if Component does not exist.

-  Postconditions

   -  The association is registered in the External Resolver, and searchable with `VIN\_SEARCH\_BY\_COMP <#VIN_SEARCH_BY_COMP>`__ and `COMP\_SEARCH\_BY\_VIN <#COMP_SEARCH_BY_VIN>`__

`VIN\_ADD\_PACKAGE <#VIN_ADD_PACKAGE>`__ Associate a Package with a VIN
-----------------------------------------------------------------------

Associates a previously provisioned software Package as being installed on a given VIN

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - An ADD\_PACKAGE command is sent from Web Server to External Resolver with a Package and a VIN

   -  E2 - The provided VIN is retrieved from External Resolver Database

   -  E3 - The provided Package is retrieved from External Resolver Database

   -  E4 - The Package is marked as installed on the VIN in External Resolver Database

   -  E5 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - VIN does not exist - an error code is sent back to Web Server. Triggered at E2 if VIN does not exist.

   -  X2 - Package does not exist - an error code is sent back to Web Server. Triggered at E3 if Package does not exist.

-  Postconditions

   -  The association is registered in the External Resolver, and searchable with `PACKAGE\_SEARCH\_BY\_VIN <#PACKAGE_SEARCH_BY_VIN>`__

`VIN\_DELETE\_PACKAGE <#VIN_DELETE_PACKAGE>`__ Remove a Package from a VIN
--------------------------------------------------------------------------

Removes an existing reference for a software package as being install on a VIN

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A DELETE\_PACKAGE command is sent from Web Server to External Resolver with a Package and a VIN

   -  E2 - The reference to the Package being installed on the VIN is removed

   -  E3 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - The Package is not registered as installed on the VIN - an error code is sent back to Web Server. Triggered at E2.

-  Postconditions

   -  None

`COMP\_SEARCH <#COMP_SEARCH>`__ Search for a Component
------------------------------------------------------

Search for one or more components based on a regexp search pattern

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A SEARCH\_COMPONENT command is sent from Web Server to External Resolver with a POSIX-style regular expression for the part numbers of interest

   -  E2 - The External Resolver Database is searched for all Components matching the part number regular expression

   -  E3 - The part numbers of all matching Components are returned

-  Exceptions

   -  None

-  Postconditions

   -  None

`VIN\_SEARCH\_BY\_COMP <#VIN_SEARCH_BY_COMP>`__ Find VINs by Component
----------------------------------------------------------------------

Find and return all VINs that have been associated with a specific Component

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A SEARCH\_COMPONENT command is sent from Web Server to External Resolver with specific Component ID (part number) of interest

   -  E2 - The Component is retrieved from the External Resolver Database

   -  E3 - All VINs associated with the Component ID (part number) are retrieved from the External Resolver Database

   -  E4 - The retrieved VINs are returned by External Resolver to Web Server

-  Exceptions

   -  X1 - Component does not exist - an error code is sent back to Web Server. Triggered at E2 if Component ID is not found.

-  Postconditions

   -  None

`COMP\_SEARCH\_BY\_VIN <#COMP_SEARCH_BY_VIN>`__ Find Components associated with a VIN
-------------------------------------------------------------------------------------

Find and return part numbers of all Components installed on a specific VIN

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A SEARCH\_COMPONENT command is sent from Web Server to External Resolver with specific VIN (not regexp) of interest

   -  E2 - The VIN is retrieved from the External Resolver Database

   -  E3 - All Components associated with the VIN are retrieved from the External Resolver Database

   -  E4 - The retrieved Components are returned by External Resolver to Web Server

-  Exceptions

   -  X1 - VIN does not exist - an error code is sent back to Web Server. Triggered at E2 if VIN is not found.

-  Postconditions

   -  None

`PACKAGE\_ADD\_DEPENDENCY <#PACKAGE_ADD_DEPENDENCY>`__ Create Dependency between Packages
-----------------------------------------------------------------------------------------

Specifies that a software Package needs another software Package in order to function properly when installed on a Component.

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  Both references software Packages have been added with PACKAGE\_ADD

-  Steps

   -  E1 - An ADD\_PACKAGE\_DEPENDENCY command is sent from Web Server to External Resolver with the Package ID that has a dependency and the Package ID that is depended upon.

   -  E2 - The Package for the dependent Package ID is retrieved from the External Resolver Database.

   -  E3 - The Package for the depended Package ID is retrieved from the External Resolver Database.

   -  E4 - The unidirectional Dependency between the two Packages is stored in the External Resolver Database.

   -  E5 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - Dependent Package ID does not exist. Triggered by E2

   -  X2 - Depended Package ID does not exist. Triggered by E3

`PACKAGE\_GET\_DEPENDENCIES <#PACKAGE_GET_DEPENDENCIES>`__ Get Dependencies for a Package
-----------------------------------------------------------------------------------------

Retrieves the IDs of all Packages that the provided Package needs in order to operate on a Component. Recursive Dependencies are an option.

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A GET\_PACKAGE\_DEPENDENCIES command is sent from Web Server to External Resolver with a software Package ID and an optional recursive resolve flag.

   -  E2 - The Package is retrieved from the External Resolver Database.

   -  E3 - The Package Dependencies are retrieved from the External Resolver Database.

   -  E4 - If the recursive resolve flag is set, E3 is executed for each located Dependency, resulting in a complete Dependency Graph including all Packages needed to run the provided Package ID on a Component.

   -  E5 - All retrieved Dependencies are returned, where each Dependency contains the Package ID of the depended-upon Package and the ID of the Package that is dependent on it.

      ::

          Dependency for A1 is { A1, { B1, B2 { C1, { D1, D2 } } } }
          Returns:
            D1 -> C1
            D2 -> C2
            C1 -> B2
            B2 -> A1
            B1 -> A1

-  Exceptions

   -  X1 - Package does not exist. Triggered by E2

`PACKAGE\_DEL\_DEPENDENCY <#PACKAGE_DEL_DEPENDENCY>`__ Remove Dependency between Packages
-----------------------------------------------------------------------------------------

Deletes a dependency between two software packages previously added with `PACKAGE\_ADD\_DEPENDENCY <#PACKAGE_ADD_DEPENDENCY>`__.

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  A Dependency has previously been set up by PACKAGE\_ADD\_DEPENDENCY

-  Steps

   -  E1 - A DELETE\_PACKAGE\_DEPENDENCY command is sent from Web Server to External Resolver with the IDs of the dependent and depended-upon Packages

   -  E2 - Dependent Package is retrieved from External Resolver Database

   -  E3 - Depended-upon Package is retrieved from External Resolver Database

   -  E4 - External Resolver Database is searched for the matching unidircetional Dependency

   -  E5 - The unidirectional dependency is deleted from the External Resolver Database

   -  E6 - A success code is sent back to Web Server

-  Exceptions

   -  X1 - Dependent Package does not exist. Triggered by E2

   -  X2 - Depended-upon Package does not exist. Triggered by E3

   -  X3 - Dependency relation could not be found. Triggered by E4

`PACKAGE\_SEARCH\_BY\_VIN <#PACKAGE_SEARCH_BY_VIN>`__ Find VINs by Package
--------------------------------------------------------------------------

Retrieves all VINs with a specific Package installed on them

-  Actors

   -  Web Server

   -  External Resolver

-  Preconditions

   -  None

-  Steps

   -  E1 - A SEARCH\_PACKAGE\_BY\_VIN command is sent from Web Server to External Resolver

   -  E2 - Package is retrieved from External Resolver Database

   -  E3 - All VINs with Package installed are retrieved from External Resolver Database

   -  E4 - A success code is sent back to Web Server with all VINs that have the Package installed

-  Exceptions

   -  X1 - Package does not exist. Triggered by E2

Installation Queue Management (SOTA Server)
===========================================

`QUEUE\_REQUEST\_ADD <#QUEUE_REQUEST_ADD>`__ Queue a package for installation on VINs matching Filter
-----------------------------------------------------------------------------------------------------

Queues a package for distribution to all VINs that match a provided boolean algebra filter

-  Actors

   -  Web Server

   -  External Resolver

   -  SOTA Server

-  Preconditions

   -  Package added with `PACKAGE\_ADD <#PACKAGE_ADD>`__

-  Steps

   -  E1 - A QUEUE\_PACKAGE request is sent from Web Server to SOTA Server with a Package ID, a Priority, and a Date/time Interval in which the install must happen

   -  E2 - The database is searched for the Package ID

   -  E3 - A Resolve VIN command is sent from SOTAServer to Resolver

   -  E4 - External Resolver searches its database for all Filters associated with Package

   -  E5 - All VINs are consecutively run through all Filters

   -  E6 - External Resolver returns the subset of VINs passing all Filters to SOTA Server, where each VIN has a list of dependent-on Packages that need to be bundled with the update for the install to succeed on that VIN

   -  E7 - SOTA Server creates a software update generated for each VIN returned by External Resolver, containing the package IDs of main and dependent-on packages to install, the date/time interval provided in E1, the priority provided in E1, and a creation date/time stamp set to the current time.

   -  E8 - A unique Install Request ID, used in all future references to the Install Request, is returned by SOTA Server to Web Server

-  Exceptions

   -  X1 - Package ID does not exist. Triggered by E2

   -  A1.1 - Resolver returns all provisioned VINs to SOTA Server. Triggered by E4. Continue execution at E7.

`QUEUE\_REQUEST\_CANCEL <#QUEUE_REQUEST_CANCEL>`__ Cancel a previous Installation Request
-----------------------------------------------------------------------------------------

Cancels a previously added install request.

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  `QUEUE\_REQUEST\_ADD <#QUEUE_REQUEST_ADD>`__ called to setup the Install Request that is to be cancelled

-  Steps

   -  E1 - A CANCEL\_PACKAGE request is sent from Web Server to SOTA Server with an Install Request ID

   -  E2 - SOTA Server database is searched for the Install Request ID

   -  E3 - Each VIN that has an update generated from the Install Request is retrieved from SOTA Server database

   -  E4 - Each VIN that is still marked as pending is removed, and is marked as canceled.

   -  E5 - Each VIN that is marked as being in flight is ignored. (If the update is currently being transmitted to its target VIN, it is allowed to complete.)

   -  E6 - Each VIN that is marked as completd is ignored.

   -  E7 - A success code is returend by SOTA Server to Web Server

-  Exceptions

   -  X1 - Install Request ID does not exist. Triggered by E2

`QUEUE\_GET\_STATUS <#QUEUE_GET_STATUS>`__ Get status for an Installation Request
---------------------------------------------------------------------------------

Retrieve status for an install request previously setup with `QUEUE\_REQUEST\_ADD <#QUEUE_REQUEST_ADD>`__

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  `QUEUE\_REQUEST\_ADD <#QUEUE_REQUEST_ADD>`__ called to setup the Install Request that is to be queried

-  Steps

   -  E1 - A GET\_INSTALL\_REQUEST\_STATUS request is sent from Web Server to SOTA Server with an Install Request ID

   -  E2 - SOTA Server database is searched for the Install Request ID

   -  E3 - The number of VINs where the Install Request has completed is calculated

   -  E4 - The number of VINs where the Install Request is still pending is calculated

   -  E5 - The number of VINs where the Install Request has failed is calculated

   -  E6 - A success code is returned by SOTA Server to Web Server together with the number of completed, in-flight, pending and failed updates

-  Exceptions

   -  X1 - Install Request ID does not exist. Triggered by E2

`QUEUE\_GET\_COMPLETED\_VINS <#QUEUE_GET_COMPLETED_VINS>`__ List VINs for which installation is complete for Installation Request
---------------------------------------------------------------------------------------------------------------------------------

Retrieve all completed VINs for a given Install Request ID

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  None

-  Steps

   -  E1 - A GET\_INSTALL\_REQUEST\_COMPLETED request is sent from Web Server to SOTA Server with an Install Request ID

   -  E2 - SOTA Server database is searched for the Install Request ID

   -  E3 - Each VIN that has successfully completed the Install Request is retrieved, together with the timestamp of completion, from the database

   -  E4 - A success code is returned by SOTA Server to Web Server together with all retrieved VINs

-  Exceptions

   -  X1 - Install Request ID does not exist. Triggered by E2

`QUEUE\_GET\_PENDING\_VINS <#QUEUE_GET_PENDING_VINS>`__ List VINs for which installation is pending for Installation Request
----------------------------------------------------------------------------------------------------------------------------

Retrieve all pending VINs for a given Install Request ID

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  `QUEUE\_REQUEST\_ADD <#QUEUE_REQUEST_ADD>`__ called to setup the Install Request that is to be queried

-  Steps

   -  E1 - A GET\_INSTALL\_REQUEST\_COMPLETED request is sent from Web Server to SOTA Server with an Install Request ID

   -  E2 - SOTA Server database is searched for the Install Request ID

   -  E3 - Each VIN that is still pending to receive the software update as part of the specified Install Request is retrieved from the database

   -  E4 - A success code is returned by SOTA Server to Web Server together with all retrieved VINs

-  Exceptions

   -  X1 - Install Request ID does not exist. Triggered by E2

`QUEUE\_GET\_IN\_FLIGHT\_VINS <#QUEUE_GET_IN_FLIGHT_VINS>`__ List VINs for which installation is ongoing for Installation Request
---------------------------------------------------------------------------------------------------------------------------------

Retrieve install requests for a given Install Request ID, which have initiated their transfers to their target VINs, but have yet to complete the transmission and be installed

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  `QUEUE\_REQUEST\_ADD <#QUEUE_REQUEST_ADD>`__ called to setup the Install Request that is to be queried

-  Steps

   -  E1 - A GET\_INSTALL\_REQUEST\_COMPLETED request is sent from Web Server to SOTA Server with an Install Request ID

   -  E2 - SOTA Server database is searched for the Install Request ID

   -  E3 - Each VIN that has successfully completed the Install Request is retrieved, together with the timestamp of completion, from the database

   -  E4 - A success code is returned by SOTA Server to Web Server together with all retrieved VINs

-  Exceptions

   -  X1 - Install Request ID does not exist. Triggered by E2

`QUEUE\_GET\_FAILED\_VINS <#QUEUE_GET_FAILED_VINS>`__ List VINs for which installation failed for Installation Request
----------------------------------------------------------------------------------------------------------------------

Retrieve install requests for a given Install Request ID which have failed

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  `QUEUE\_REQUEST\_ADD <#QUEUE_REQUEST_ADD>`__ called to setup the Install Request that is to be queried

-  Steps

   -  E1 - A GET\_INSTALL\_REQUEST\_COMPLETED request is sent from Web Server to SOTA Server with an Install Request ID

   -  E2 - SOTA Server database is searched for the Install Request ID

   -  E3 - All VINs that have failed to receive a software update as a part of the specified Request ID are retrieved, together with an error code and a time stamp, from the database

   -  E4 - A success code is returned by SOTA Server to Web Server together with all retrieved VINS and their error codes and time stamps.

-  Exceptions

   -  X1 - Install Request ID does not exist. Triggered by E2

`QUEUE\_GET\_NEXT\_SOFTWARE\_UPDATE <#QUEUE_GET_NEXT_SOFTWARE_UPDATE>`__ Get current or next queued Installation Request for VIN
--------------------------------------------------------------------------------------------------------------------------------

Sub use case used by `QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__ and `TRANSFER\_START <#TRANSFER_START>`__ to determine which software update to transmit next to a specific VIN

-  Actors

   -  External Resolver

   -  SOTA Server

-  Preconditions

   -  Invoked by `QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__ or `TRANSFER\_START <#TRANSFER_START>`__

-  Steps

   -  E1 - SOTA Server checks if there is a software update marked as in-flight for the targeted VIN

   -  E2 - If an in-flight update was found, it is returned to the invoker of this use case. End of use case

   -  E3 - SOTA Server retrieves all currently pending software updates for the target VIN from the database

   -  E4 - All retrieved software updates are sorted by the priority provided to QUEUE\_REQUEST\_ADD when the updates were created

   -  E5 - All software updates with the same priority are sorted by their creation date/time stamp.

   -  E6 - The software update at the top of the priority- and date/time stamp-sorted list is retrieved for transfer, including all its dependent-upon packages

   -  E7 - The size of the software update is verified to be less than the remaining bytes of the active billing cycle of the data plan used by the target VIN

-  Exceptions

   -  A1 - No packages are pending for the VIN. Use case returns with a nothing-to-do answer. Triggered by E3

   -  A2 - No data plan has been set by VIN. Use case returns successfully with the given software update. Triggered by E7

   -  A3 - Software update size is greater than remaining size of current billing cycle. Use case returns an over size error. Triggered by E7

`QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__
------------------------------------------------

Periodically go through all queued software updates targeting VINs and initiate the transmission of those ready to send

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  High-level scheduler triggers this use case periodically

-  Steps

   -  E1 - All VINs with pending software or in-flight updates are retrieved. (Failed, completed, and in-flight updates are ignored.)

   -  E2 - Each VIN is traversed in a non-specified order

   -  E3 - If the currently traversed VIN’s Device is connected to SOTA Server, the VIN is skipped. (Ignore VINs that are currently being communicated with.)

   -  E4 - If the currently traversed VIN has had DEV\_WAKEUP, or DEV\_DISCONNECT executed within the number of seconds specified by the VIN’s reconnect interval provided to VIN\_ADD, the VIN is skipped. (Ignore VINs that we’ve tried to communicate with during the last number of seconds specified by the reconnect interval. Avoids continuous reconnect attempts.)

   -  E5 - Use case `QUEUE\_GET\_NEXT\_SOFTWARE\_UPDATE <#QUEUE_GET_NEXT_SOFTWARE_UPDATE>`__ is executed to retrieve the next in-flight or pending software to (continue to) send to the VIN

   -  E6 - Send a wakeup signal to trigger DEV\_WAKEUP on the currently traversed VIN. (Wakeup/shoulder tap SMS)

   -  E7 - A success code is returned together with the number VINs that have been sent a wakeup signal

-  Exceptions

   -  A1 - `QUEUE\_GET\_NEXT\_SOFTWARE\_UPDATE <#QUEUE_GET_NEXT_SOFTWARE_UPDATE>`__ returns 'nothing-to-do'. Use case continues at E3 with the next VIN from the list retrieved in E1. Triggered by E5

   -  A2 - `QUEUE\_GET\_NEXT\_SOFTWARE\_UPDATE <#QUEUE_GET_NEXT_SOFTWARE_UPDATE>`__ returns oversize error. Use case continues at E3 with the next VIN from the list retrieved in E1. (Will leave the oversized update as pending until the next billing cycle for the data plan used by the VIN becomes active.) Triggered by E5

`QUEUE\_PURGE <#QUEUE_PURGE>`__ Purge failing or expired Installation Requests from Queue
-----------------------------------------------------------------------------------------

Periodically go through all pending software updates that are not complete, failed, or in flight and remove those whose date/time install interval has expired.

-  Actors

   -  SOTA Server

-  Preconditions

   -  High-level scheduler triggers this use case periodically

-  Steps

   -  E1 - All pending software updates are retrieved from the database.

   -  E2 - Each pending software update has its date/time Install Interval compared with the current date and time.

   -  E3 - If the current date/time is before or inside of the software update’s Install Interval, it will not be touched, and the next software pending software update is examined

   -  E4 - If the current date/time is after the software update’s Install Interval, it will be marked as failed. (The software update will be returned in future calls to `QUEUE\_GET\_FAILED\_VINS <#QUEUE_GET_FAILED_VINS>`__)

   -  E5 - The failed update will have an error code set as "expired"

   -  E6 - The failed update will have a failure date/time stamp set to the current time.

   -  E7 - A success code is returned together with the number of purged updates.

-  Exceptions

   -  None

`QUEUE\_REQUEST\_GET\_ALL\_PACKAGES <#QUEUE_REQUEST_GET_ALL_PACKAGES>`__ Get list of Packages queued for a VIN
--------------------------------------------------------------------------------------------------------------

A request to retrieve a list of all installed packages is queued for a specific VIN

-  Actors

   -  Web Server

   -  SOTA Server

-  Preconditions

   -  VIN added with `VIN\_ADD <#VIN_ADD>`__

-  Steps

   -  E1 - A GET\_ALL\_PACKAGES request is sent from Web Server to SOTA Server with a VIN to retrieve the installed software list

      -  The date/time interval specifies an earliest and latest install date and time stamp within which the install must be initiated

   -  E2 - The database is searched for the VIN

   -  E3 - SOTA Server creates a GET\_ALL\_PACKAGES request containing the VIN, a default date/time interval, a default priority, and a creation date/time stamp set to the current time.

   -  E4 - A unique Request ID, used in all future references to the installation request, is returned by SOTA Server to Web Server

-  Exceptions

   -  X1 - The VIN does not exist. An error code is sent back to Web Server

Device Interaction
==================

`DEV\_WAKEUP <#DEV_WAKEUP>`__ Send Wake-up event to VIN, triggering `DEV\_CONNECT <#DEV_CONNECT>`__
---------------------------------------------------------------------------------------------------

A Device receives a wakeup notification sent by a `QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__ use case and will start the download and install software update process.

-  Actors

   -  Device

-  Preconditions

   -  None

-  Steps

   -  E1 - The Device receives a wakeup notification via a mobile or other network trigger mechanism.

   -  E2 - The Device uses PKI-based signatures to validate that the wakeup notification is from SOTA Server

   -  E3 - The `DEV\_CONNECT <#DEV_CONNECT>`__ use cases is executed.

-  Exceptions

   -  X1 - PKI validation failed. The message is ignored and the use cases is terminated. Triggered by E2

`DEV\_CONNECT <#DEV_CONNECT>`__ Device connects to SOTA Server to process or resume Installation Request
--------------------------------------------------------------------------------------------------------

The device connects to SOTA Server in order to start or continue a download of a software update targeting the VIN of the device.

-  Actors

   -  Device

   -  SOTA Server

-  Preconditions

   -  `DEV\_WAKEUP <#DEV_WAKEUP>`__ executed, or periodic server connect occurs.

-  Steps

   -  E1 - The Device sets up a network connection

   -  E2 - The Device connects to the predefined SOTA Server

   -  E3 - The Device authenticates itself to the SOTA Server

   -  E4 - The SOTA Server authenticates itself to the Device

   -  E5 - Use case transitions to `UPDATE\_NOTIFICATION <#UPDATE_NOTIFICATION>`__

-  Exceptions

   -  X1 - Network connection failed. Triggered by E1.

      -  If this is the N:th time that `DEV\_CONNECT <#DEV_CONNECT>`__ has failed to connect, the use case is terminated

      -  A preconfigured incremental waiting period is setup

      -  The `DEV\_CONNECT <#DEV_CONNECT>`__ use case is executed again

   -  X2 - Device Authentication fails. Use case transitions to `DEV\_DISCONNECT <#DEV_DISCONNECT>`__. Triggered by E3

   -  X3 - SOTA Server Authentication fails. Use case transitions to `DEV\_DISCONNECT <#DEV_DISCONNECT>`__. Triggered by E4

   -  A1 - `TRIGGER\_TRANSFER\_START <#TRIGGER_TRANSFER_START>`__ is waiting to have its message sent to SOTA Server. The use case transitions to `TRIGGER\_TRANSFER\_START <#TRIGGER_TRANSFER_START>`__-E2. Triggered by E5.

   -  A2 - `TRANSFER\_START <#TRANSFER_START>`__ is waiting to have its message sent to Device. The use case transitions to `TRANSFER\_START <#TRANSFER_START>`__-E2. Triggered by E5.

   -  A3 - `TRANSFER\_CHUNK <#TRANSFER_CHUNK>`__ is waiting to have its message sent to Device. The use case transitions to `TRANSFER\_CHUNK <#TRANSFER_CHUNK>`__-E2. Triggered by E5.

   -  A4 - `TRANSFER\_COMPLETE <#TRANSFER_COMPLETE>`__ is waiting to have its message sent to Device. The use case transitions to `TRANSFER\_COMPLETE <#TRANSFER_COMPLETE>`__-E1. Triggered by E5.

`DEV\_DISCONNECT <#DEV_DISCONNECT>`__ SOTA Server instructs Device to disconnect
--------------------------------------------------------------------------------

Disconnect a server session

-  Actors

   -  Device

   -  SOTA Server

-  Preconditions

   -  Multiple

-  Steps

   -  E1 - SOTA Server sends Disconnect command to Device

   -  E2 - Device terminates network connection

   -  E3 - Device schedules next time to execute `DEV\_CONNECT <#DEV_CONNECT>`__

-  Exceptions

   -  X1 - Network connection lost before disconnect is received by device. Triggered by E1. `DEV\_CONNECT <#DEV_CONNECT>`__ is executed X times in order to reconnet to the server.

`UPDATE\_NOTIFICATION <#UPDATE_NOTIFICATION>`__ Send any queued Installation Requests (`QUEUE\_GET\_NEXT\_SOFTWARE\_UPDATE <#QUEUE_GET_NEXT_SOFTWARE_UPDATE>`__) to Device
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Send a notification of available software updates to vehicle

-  Actors

   -  Device

   -  SOTA Server

-  Preconditions

   -  `DEV\_CONNECT <#DEV_CONNECT>`__ has been executed to setup and authenicate a SOTA Server - Device connection.

-  Steps

   -  E1 - Use case `QUEUE\_GET\_NEXT\_SOFTWARE\_UPDATE <#QUEUE_GET_NEXT_SOFTWARE_UPDATE>`__ is executed to retrieve the next pending or in-flight update to transfer / continue.

   -  E2 - A SOFTWARE\_UPDATE\_AVAILABLE command is sent by SOTA Server to Device with the Package IDs included in the download, size, a download index and a descriptive string

   -  E3 - Device forwards the update information to the Software Loading Manager. The Software Loading Manager will either wait for a user confirmation, or automatically initiate the download.

   -  E4 - Use case transitions to `TRIGGER\_TRANSFER\_START <#TRIGGER_TRANSFER_START>`__

-  Exceptions

   -  X1 - Network connection lost before update is received by device. Triggered by E2. `DEV\_CONNECT <#DEV_CONNECT>`__ is executed X times in order to reconnet to the server.

`TRIGGER\_TRANSFER\_START <#TRIGGER_TRANSFER_START>`__ Software Loading Manager requests download from SOTA Server via SOTA Client
----------------------------------------------------------------------------------------------------------------------------------

Send a request to start the transfer from

-  Actors

   -  Device

   -  SOTA Server

-  Preconditions

   -  `UPDATE\_NOTIFICATION <#UPDATE_NOTIFICATION>`__ has been executed. Connection is up.

-  Steps

   -  E1 - An INITIATE\_SOFTWARE\_DOWNLOAD command is sent by Software Loading Manager to SOTA Client on Device

   -  E2 - An INITIATE\_SOFTWARE\_DOWNLOAD command is forwarded by Device to SOTA Server together with the download index provided by `UPDATE\_NOTIFICATION <#UPDATE_NOTIFICATION>`__

   -  E3 - Use case transitions to `TRANSFER\_START <#TRANSFER_START>`__

-  Exceptions

   -  A1 - Software Loading Manager cancels download instead of starting it. Triggered by E1.

      -  A CANCEL\_SOFTWARE\_DOWNLOAD is forwarded by Device to SOTA Server together with the update notification

      -  Use case transitions to `DEV\_DISCONNECT <#DEV_DISCONNECT>`__

   -  X1 - Network connection lost before Initiate / Cancel Software Download is sent. Triggered by E2. Use case transitions to `DEV\_CONNECT <#DEV_CONNECT>`__.

`TRANSFER\_START <#TRANSFER_START>`__ SOTA Server sends Download metadata to Device
-----------------------------------------------------------------------------------

Start transfer of an update.

-  Actors

   -  Device

   -  SOTA Server

-  Preconditions

   -  `DEV\_CONNECT <#DEV_CONNECT>`__ has been executed to setup and authenticate a SOTA Server - Device connection *OR*

   -  `INSTALL\_REPORT <#INSTALL_REPORT>`__ has been executed to signal the success or failure of a previous install

-  Steps

   -  E1 - An INITIATE\_SOFTWARE\_DOWNLOAD command is received by SOTA Server from Device.

   -  E2 - An START\_DOWNLOAD command is sent by SOTA Server to Device with the Package IDs to be installed and the total size of the transfer.

   -  E3 - Device verifies that it has the resources to receive the package from SOTA Server.

   -  E4 - Use case transitions to `TRANSFER\_CHUNK <#TRANSFER_CHUNK>`__

-  Exceptions

   -  A1.1 - CANCEL\_SOFTWARE\_DOWNLOAD received. Triggered by E1. Use case transitions to `INSTALL\_REPORT <#INSTALL_REPORT>`__ with a CANCELLED result code. (Software update was cancelled by Software Loading Manager, possibly after the user pressed "no" in a confirmation dialog).

   -  A1 - Network connection was lost before START\_DOWNLOAD command was sent. Triggered by E1. Use case transitions to `DEV\_DISCONNECT <#DEV_DISCONNECT>`__. (No packages available for transfer since they were cancelled between `QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__ and this use case)

   -  A2 - Software update is marked as in-flight. Use case transitions to `TRANSFER\_CHUNK <#TRANSFER_CHUNK>`__. (We are picking up a previously interrupted software update transfer and want to move on to the next chunk of the update)

   -  A3 - Next element in queue is a GET\_ALL\_PACKAGES request. Use case transitions to `GET\_ALL\_PACKAGES <#GET_ALL_PACKAGES>`__

`TRANSFER\_CHUNK <#TRANSFER_CHUNK>`__ SOTA Server sends next Download chunk to Device
-------------------------------------------------------------------------------------

Transfer a chunk of data for an update

-  Actors

   -  Device

   -  SOTA Server

-  Preconditions

   -  `TRANSFER\_START <#TRANSFER_START>`__ has been executed.

   -  Device is connected to SOTA Server

-  Steps

   -  E1 - SOTA Server retrieves the lowest numbered chunk (data block) that has yet to be transferred to Device

   -  E2 - SOTA Server transmits chunk to Device

   -  E3 - Device receives chunk

   -  E4 - Device stores chunk at its correct position in the package being built up

   -  E5 - Device sense acknowledgement of successful chunk receipt to SOTA Server

   -  E6 - SOTA Server marks chunk as successfully transmitted

   -  E7 - Use case restarts at E1 with next untransmitted chunk

-  Exceptions

   -  A1 - No more chunks to transmit. Triggered by E1. Use case transitions to `TRANSFER\_COMPLETE <#TRANSFER_COMPLETE>`__

   -  X1 - Network connection is lost before chunk is received by Device. Triggered by E2. `DEV\_CONNECT <#DEV_CONNECT>`__ is executed X times in order to reconned to the server. After X times, we rely on `QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__ for future retries.

   -  A2 - Chunk has already been received. Triggered by E4. (Retransmit of chunks are allowed in case the ack in E5 is lost).

      -  A2.1 - New chunk is dropped

      -  A2.2 - Use case continues at E5

   -  X2 - Network connection lost before acknowledgement is received by SOTA Server. Triggered by E5. `DEV\_CONNECT <#DEV_CONNECT>`__ is executed X times in order to reconnect to the server. Chunk will be retransmitted, and E4.A1 will handle the case. After X time, we rely on `QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__ for future retries.

`TRANSFER\_COMPLETE <#TRANSFER_COMPLETE>`__ SOTA Server sends Finalize Download to Device
-----------------------------------------------------------------------------------------

Finalize an update transfer

-  Actors

   -  Device

   -  SOTA Server

-  Preconditions

   -  Called by `TRANSFER\_CHUNK <#TRANSFER_CHUNK>`__-A1.

-  Steps

   -  E1 - SOTA Server sends FINALIZE\_DOWNLOAD command to Device

   -  E2 - Device validates that all chunks have been received

   -  E3 - SOTA Server marks software update as in-flight with 0 bytes left to transmit.

   -  E4 - Use case transitions to `INSTALL\_SOFTWARE\_UPDATE <#INSTALL_SOFTWARE_UPDATE>`__

-  Exceptions

   -  X1 - Network connection lost before FINALIZE\_DOWNLOAD command is received by Device. Triggered by E1. `DEV\_CONNECT <#DEV_CONNECT>`__ is executed X times in order to reconnect to the server. After X times we rely on `QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__ for future retries.

   -  X2.1 - Chunks are missing on Device, even if SOTA Server believes all have been transmitted. Triggered by E2. Use case transitions to `INSTALL\_SOFTWARE\_UPDATE <#INSTALL_SOFTWARE_UPDATE>`__ with an INCOMPLETE\_DOWNLOAD result code.

`INSTALL\_SOFTWARE\_UPDATE <#INSTALL_SOFTWARE_UPDATE>`__ Device installs all received Packages
----------------------------------------------------------------------------------------------

Validate and install all packages received in a software update from SOTA Server.

-  Actors

   -  Device

-  Preconditions

   -  `TRANSFER\_COMPLETE <#TRANSFER_COMPLETE>`__ executed.

-  Steps

   -  E1 - Device verifies signature and integrity of software updates.

   -  E2 - Device sends an INSTALL command to the Software Loading Manager.

   -  E3 - Software Loading Manager returns an installation result code and descriptive text.

   -  E4 - The installation result is forwarded to the `INSTALL\_REPORT <#INSTALL_REPORT>`__ use case.

-  Exceptions

   -  X1 - Package validation fails. Triggered by E1. Use case transitions to `INSTALL\_REPORT <#INSTALL_REPORT>`__ with a VALIDATION\_FAIL result code.

`INSTALL\_REPORT <#INSTALL_REPORT>`__ Device reports Installation Result to SOTA Server
---------------------------------------------------------------------------------------

Report installation success or failure

-  Actors

   -  Device

   -  SOTA Server

   -  External Resolver

-  Preconditions

   -  `INSTALL\_SOFTWARE\_UPDATE <#INSTALL_SOFTWARE_UPDATE>`__ executed.

-  Steps

   -  E1 - Device sends report with provided result code to SOTA Server

   -  E2 - If result code is SUCCESS, the software update for the VIN is marked as completed.

   -  E3 - If result code is not SUCCESS, the software update for the VIN is marked as failed together with provided result code.

   -  E4 - If result code is SUCCESS, the `VIN\_ADD\_PACKAGE <#VIN_ADD_PACKAGE>`__ use case is executed to update the installed package list of the External Resolver Database.

   -  E5 - Use case transitions to `TRANSFER\_START <#TRANSFER_START>`__ to start the transmission of the next software update for the VIN.

-  Exceptions

   -  X1 - Network connection lost before report is received by SOTA Server. Triggered by E1. `DEV\_CONNECT <#DEV_CONNECT>`__ is executed X times in order to reconnect to the server.

   -  A1 - VIN is already marked as completed. Triggered by E2. Use case transitions to `TRANSFER\_START <#TRANSFER_START>`__.

   -  A2 - VIN is already marked as failed. Triggered by E3. Use case transitions to `TRANSFER\_START <#TRANSFER_START>`__.

`GET\_ALL\_PACKAGES <#GET_ALL_PACKAGES>`__ Get list of Packages installed on a VIN (from the Device)
----------------------------------------------------------------------------------------------------

Retrieve all packages currently installed on a device

-  Actors

   -  Device

   -  SOTA Server

-  Preconditions

   -  `DEV\_CONNECT <#DEV_CONNECT>`__ has been executed to setup and authenticate a SOTA Server - Device connection *OR*

   -  `INSTALL\_REPORT <#INSTALL_REPORT>`__ has been executed to signal the success or failure of a previous install.

-  Steps

   -  E1 - Use case `QUEUE\_GET\_NEXT\_SOFTWARE\_UPDATE <#QUEUE_GET_NEXT_SOFTWARE_UPDATE>`__ is executed to retrieve the next pending or in-flight update to transfer / continue, yielding instead a queued GET\_ALL\_PACKAGES request.

   -  E2 - A GET\_ALL\_PACKAGES command is sent by SOTA Server to Device.

   -  E3 - Device uses local package manager to retrieve a list of all installed packages

   -  E4 - Device returns all installed packages to SOTA Server.

   -  E4.1 - SOTA Server uses `VIN\_PACKAGE\_ADD <#VIN_PACKAGE_ADD>`__ and `VIN\_PACKAGE\_DELETE <#VIN_PACKAGE_DELETE>`__ to synchronize External Resolver’s installed package list for the given VIN.

   -  E5 - Use case transitions to `TRANSFER\_START <#TRANSFER_START>`__ to start.

-  Exceptions

   -  X3 - Acknowledgement lost due to network disconnect. Triggered by E4. `DEV\_CONNECT <#DEV_CONNECT>`__ is executed X times in order to reconnect to the server. After X times, we rely on `QUEUE\_INITIATE\_XMIT <#QUEUE_INITIATE_XMIT>`__ for future retries.
