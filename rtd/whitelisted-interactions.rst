layout: page title: "Whitelisted Interactions" category: sec date: 2015-08-26 10:34:14 --- :toc: macro :icons: font

This page details all currently whitelisted interactions. When creating stories in JIRA, you must note any interactions between components that will be required. If they are not already covered by a whitelisted interaction in this list, you must review them with the project security curator. If the new interactions are approved, they will be added to this list. Code **will not be merged** if it contains non-whitelisted interactions.

Whitelisted interactions are organized by boundary:

|Whitelisted Interactions Reference Diagram|

B-1 Web Browser - Web Server
============================

WL-B1-1 Web Server sends index.html page to Web Browser
-------------------------------------------------------

The Web Server can send the index.html file to the Web Browser, over HTTP on port 80.

-  Upon the Web Browser’s request for /index.html, the Web Server will respond with the index.html HTML file and a 200 status code.

-  Upon the Web Browser’s request for another HTML file, the Web Server will respond with a response with 409 status code.

WL-B1-2 Web Server sends Javascript scripts linked in index.html to Web Browser
-------------------------------------------------------------------------------

The Web Server can send the Javascript scripts linked in index.html to the Web Browser over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server will respond with the Javascript files linked in and a 200 status code.

-  Upon the Web Browser’s request for an unknown or not approved Javascript script, the Web Server will respond with a response with 409 status code.

WL-B1-3 Web Server sends CSS stylesheets linked in index.html to Web Browser
----------------------------------------------------------------------------

The Web Server can send the CSS Stylesheet files linked in index.html to the Web Browser over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server will respond with the CSS Stylesheet files linked in and a 200 status code.

-  Upon the Web Browser’s request for an unknown or not approved CSS Stylesheet file, the Web Server will respond with a response with 409 status code.

WL-B1-4 Web Browser sends new VIN to Web Server
-----------------------------------------------

The Web Browser can send new VIN data to the Web Server that conform to the VIN standard (17 digits long, only capital letters and numbers), using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a new VIN insertion process and if a new database entry is created, it will respond with a ‘Row Inserterd’ message in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a new VIN insertion process and if a database entry already exists, it will respond with a ‘VIN alread exists’ message in the response body and a 409 status code.

WL-B1-5 Web Browser sends new campaign data to Web Server
---------------------------------------------------------

The Web Browser can send new a Campaign (Package ID, Priority, Start Time, End Time) to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browsers’s request, the Web Server can look for the Package ID lookup process and if the Package exists, it will respond with a ‘Campaign created’ message in the response body and a 200 status code.

-  Upon the Web Browsers’s request, the Web Server can lookup for the Package ID, and if the Package does not exist, it will respond with a ‘Unknown Package ID’ message in the response body and a 404 status code.

WL-B1-6 Web Browser sends New Filter Request to Web Server
----------------------------------------------------------

The Web Browser can send a new Filter’s data to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a new Filter insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a new Filter insertion process and if a database entry already exists, it will respond with a ‘Filter already exists’ message in the response body and a 409 status code.

WL-B1-7 Web Browser sends filter-to-package association to Web Server
---------------------------------------------------------------------

The Web Browser can send a new association between a Filter and a Package to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a new Filter/Package association insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a new Filter/Package association insertion process and if a database entry already exists, it will respond with a ‘Filter/Package association already exists’ message in the response body and a 409 status code.

WL-B1-8 Web Browser sends Queue Package Request to Web Server
-------------------------------------------------------------

The Web Browser can send a Queue Package Request [Package ID, Priority, Date/Time Interval] to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a Queue Package Request process and if a request is processed without errors, it will respond with a unique Install Request ID in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Queue Package Request process and if the given package ID is not found, it will respond with a ‘Package ID does not exist’ message in the response body and a 404 status code.

-  Upon the Web Browser’s request, the Web Server can start a Queue Package Request process and if no filters are associated with the package, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B1-9 Web Server sends login page and assets to Web Browser
-------------------------------------------------------------

-  Upon receiving unauthenticated request from Web Browser, Web Server sends login page HTML and assets over HTTPS (port 443) to Web Browser.

WL-B1-10 Web Browser sends login credentials to Web Server
----------------------------------------------------------

-  When user enters and submit credentials in Web Browser, Web Browser sends the credentials to Web Server over HTTPS POST (port 443),

-  If the credentials are valid, Web Server sends Admin GUI page content and session cookie to Web Browser over HTTPS (port 443)

-  If the credentials are invalid, Web Server sends Access Denied (HTTP 401) to Web Browser over HTTPS (port 443)

WL-B1-11 Web Server sends session cookie to Web Browser
-------------------------------------------------------

-  In response to a request from a Web Browser, Web Server may include an HTTP Cookie to establish a session

WL-B1-12 Web Browser sends session cookie to Web Server
-------------------------------------------------------

-  When requesting resources from Web Server, Web Browser may include any locally stored HTTP Cookie associated with the Admin GUI domain

WL-B1-13 Web Server sends a redirection to login page for unauthenticated users
-------------------------------------------------------------------------------

-  In response to HTTPS (port 443) requests from a Web Browser, the Web Server may send HTTPS 301 redirect responses to unauthenticated clients

WL-B1-14 Web Server sends a redirection back to the last requested page for authenticated users
-----------------------------------------------------------------------------------------------

-  In response to HTTPS (port 443) requests from a Web Browser that include valid login credentials, the Web Server may send an HTTPS 301 response to authenticated clients for protected resources.

WL-B1-15 Web Browser sends a request for an Admin GUI resource to Web Server, along with a session cookie
---------------------------------------------------------------------------------------------------------

-  Web Browser may send HTTPS (port 443) requests to Web Server on behalf of user for protected Admin GUI resources

-  Requests may include any locally stored Cookies associated with the Admin GUI Domain

WL-B1-16 Web Server sends Admin GUI resource HTML and associated assets to Web Browser
--------------------------------------------------------------------------------------

-  In response to authenticated HTTPS (port 443) requests from Web Browser for protected Admin GUI resources, Web Server may send back associated HTML and resources to render resource details and necessary hyperlinks, JavaScript code, assets, etc. to Web Browser.

WL-B1-18 Web Browser sends a List Queue Package Request to Web Server
---------------------------------------------------------------------

The Web Browser can send a List Queue Package Request {[Package ID, Priority, Date/Time Interval], […]} to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a List Queue Package Request process and if a request is processed without errors, it will respond with a unique Install Request ID in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a List Queue Package Request process and if the given package ID is not found, it will respond with a ‘Package ID does not exist’ message in the response body and a 404 status code.

-  Upon the Web Browser’s request, the Web Server can start a List Queue Package Request process and if no filters are associated with the one of the packages, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B1-19 Web Browser sends a GET Queued Request to Web Server
-------------------------------------------------------------

The Web Browser can send a get Queued Package Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a get Queued Package Request process and if a request is processed without errors, it will respond with a list of queued update requests in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a get Queued Package Request process and if there are no pending update requests, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a List Queue Package Request process and if no filters are associated with the one of the packages, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B1-20 Web Browser sends Search VINs Request to Web Server
------------------------------------------------------------

The Web Browser can send a Search VINs Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a Search VINs Request process and if a request is processed without errors, it will respond with a list of VINs matching the search criteria in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Search VINs Request process and if there are no VINs matching the search criteria, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Search VINs Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-21 Web Browser sends the binary package and its metadata to Web Server
----------------------------------------------------------------------------

The Web Browser can upload a binary package and a POST request for its associated data to the Web Server.

-  Upon the Web Browser’s request, the Web Server can receive a binary package and its associated metadata and perform an Upload New Package process and if the request is processed without errors, it will respond with a message informing for successful persistence in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can receive a binary package and its associated metadata and perform an Upload New Package process and if the request is processed with errors, it will respond with a message informing for the generated error in the response body and a 500 status code.

-  Upon the Web Browser’s request, Web Server can receive the metadata associated with a new package and if SOTA Core fails to authenticate, it will respond with an ‘Authentication Failed’ message in the response body and a 404 status code.

WL-B1-22 Web Browser sends Search Filters Request to Web Server
---------------------------------------------------------------

The Web Browser can send a Search Filters Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a Search Filters Request process and if a request is processed without errors, it will respond with a list of Filters matching the search criteria in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Search Filters Request process and if there are no Filters matching the search criteria, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Search Filters Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-23 Web Browser sends List Filters Request to Web Server
-------------------------------------------------------------

The Web Browser can send a List Filters Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a Search List Request process and if a request is processed without errors, it will respond with a list of available Filters in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Search Filters Request process and if there are no available Filters, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a List Filters Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-24 Web Browser sends Update Filter Request to Web Server
--------------------------------------------------------------

The Web Browser can send a Update Filter Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a Update Filter Request process and if a request is processed without errors, it will respond with a list of available Filters in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Update Filter Request process and if there is no Filter with the given Filter ID, it will respond with a ‘Filter does not exist’ message in the response body and a 409 status code.

-  Upon the Web Browser’s request, the Web Server can start a Update Filter Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-25 Web Browser sends Delete Filter Request to Web Server
--------------------------------------------------------------

The Web Browser can send a Delete Filter Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a Delete Filter Request process and if a request is processed without errors, it will respond with a list of available Filters in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Delete Filter Request process and if there is no Filter with the given Filter ID, it will respond with a ‘Filter does not exist’ message in the response body and a 409 status code.

-  Upon the Web Browser’s request, the Web Server can start a Delete Filter Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-26 Web Browser sends Update Packages per VIN Request to Web Server
------------------------------------------------------------------------

The Web Browser can send an Update Packages per VIN Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start an Update Packages per VIN Request process and if a request is processed without errors, it will respond with the modified package data for the selected VIN in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start an Update Packages per VIN Request process and if the given VIN does not exist, it will respond with a ‘VIN does not exist’ message in the response body and a 409 status code.

-  Upon the Web Browser’s request, the Web Server can start an Update Packages per VIN Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-27 Web Browser sends View Packages per VIN Request to Web Server
----------------------------------------------------------------------

The Web Browser can send a View Packages per VIN Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a View Packages per VIN Request process and if a request is processed without errors, it will respond with the installed packages on the selected VIN in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a View Packages per VIN Request process and if the given VIN does not exist, it will respond with a ‘VIN does not exist’ message in the response body and a 409 status code.

-  Upon the Web Browser’s request, the Web Server can start a View Packages per VIN Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-28 Web Browser sends View VINs per Package Request to Web Server
----------------------------------------------------------------------

The Web Browser can send a View VINs per Package Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a View VINs per Package Request Request process and if a request is processed without errors, it will respond with the VINs that have installed the selected Package in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a View VINs per Package Request process and if the given Package does not exist, it will respond with a ‘Package does not exist’ message in the response body and a 409 status code.

-  Upon the Web Browser’s request, the Web Server can start a View VINs per Package Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-29 Web Browser sends New Component Request to Web Server
--------------------------------------------------------------

The Web Browser can send a new Component’s data to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a new Component insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a new Component insertion process and if a database entry already exists, it will respond with a ‘Component already exists’ message in the response body and a 409 status code.

WL-B1-30 Web Browser sends Search Components Request to Web Server
------------------------------------------------------------------

The Web Browser can send a Search Components Request to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can start a Search Components Request process and if a request is processed without errors, it will respond with a list of Components matching the search criteria (regex, ID/IDs or name) in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Search Components Request process and if there are no Components matching the search criteria, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Browser’s request, the Web Server can start a Search Components Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B1-31 Web Browser sends "Get Install Request Completed" Request to Web Server
--------------------------------------------------------------------------------

The Web Browser can send a "Get Install Request Completed" to the Web Server using JSON over HTTP on port 80.

-  Upon the Web Browser’s request, the Web Server can send a "Get Install Request Completed" and if the request has been processed without errors, it will respond with a list of VINs where the Install Request was successfully completed and the associated timestamp when the Request was finished in the response body and a 200 response code.

-  Upon the Web Browser’s request, the Web Server can send a "Get Install Request Completed" and if no VINs have been returned, it will return an empty list in the response body and a 200 response code.

-  Upon the Web Browser’s request, the Web Server can send a "Get Install Request Completed" and if the request has been processed with errors, it will respond with the apropriate error message in the response body and a 200 response code.

B-2 Web Server - SOTA Core
==========================

WL-B2-1 Web Server sends New Package Request to SOTA Core
---------------------------------------------------------

The Web Server can send A New Package Request to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, SOTA Core can start a new Package insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can start a new Package insertion process and if a database entry already exists, it will respond with a ‘Package already exists’ message in the response body and a 409 status code.

WL-B2-2 Web Server sends new VIN to SOTA Core
---------------------------------------------

The Web Server can send the VINs data to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, SOTA Core can start a new VIN insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can start a new VIN insertion process and if a database entry already exists, it will respond with a ‘VIN already exists’ message in the response body and a 409 status code.

WL-B2-3 Web Server sends new campaign data to SOTA Core
-------------------------------------------------------

The Web Server can send new a Campaign (Package ID, Priority, Start Time, End Time) to SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, SOTA Core can look for the Package ID lookup process and if the Package exists, it will respond with a ‘Campaign created’ message in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can lookup for the Package ID, and if the Package does not exist, it will respond with a ‘Unknown Package ID’ message in the response body and a 404 status code.

WL-B2-4 Web Server sends Queue Package Request to SOTA Core
-----------------------------------------------------------

The Web Server can send a Queue Package Request [Package ID, Priority, Date/Time Interval] to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, Core can start a Queue Package Request process and if a request is processed without errors, it will respond with a unique Install Request ID in the response body and a 200 status code.

-  Upon the Web Server’s request, Core can start a Queue Package Request process and if the given package ID is not found, it will respond with a ‘Package ID does not exist’ message in the response body and a 404 status code.

-  Upon the Web Browser’s request, the Web Server can start a Queue Package Request process and if no filters are associated with the package, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B2-5 Web Server sends a List Queue Package Request to SOTA Core
------------------------------------------------------------------

The Web Server can send a List Queue Package Request {[Package ID, Priority, Date/Time Interval], […]} to SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, SOTA Core can start a List Queue Package Request process and if a request is processed without errors, it will respond with a unique Install Request ID in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can start a List Queue Package Request process and if the given package ID is not found, it will respond with a ‘Package ID does not exist’ message in the response body and a 404 status code.

-  Upon the Web Server’s request, SOTA Core can start a List Queue Package Request process and if no filters are associated with the one of the packages, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B2-6 Web Server sends a GET Queued Request to SOTA Core
----------------------------------------------------------

The Web Server can send a get Queued Package Request to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the SOTA Core can start a get Queued Package Request process and if a request is processed without errors, it will respond with a list of queued update requests in the response body and a 200 status code.

-  Upon the Web Server’s request, the SOTA Core can start a get Queued Package Request process and if there are no pending update requests, it will respond with an empty list of queued update requests in the response body and a 200 status code.

-  Upon the Web Server’s request, the SOTA Core can start a get Queued Package Request process and if no filters are associated with the one of the packages, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B2-7 Web Server sends Search VINs Request to SOTA Core
---------------------------------------------------------

The Web Server can send a Search VINs Request to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, SOTA Core can start a Search VINs Request process and if a request is processed without errors, it will respond with a list of VINs matching the search criteria in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can start a Search VINs Request process and if there are no VINs matching the search criteria, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can start a Search VINs Request process and if no filters are associated with the one of the packages, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B2-9 Web Server sends Update Packages per VIN Request to SOTA Core
---------------------------------------------------------------------

The Web Server can send an Update Packages per VIN Request to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the SOTA Core can start an Update Packages per VIN Request process and if a request is processed without errors, it will respond with the modified package data for the selected VIN in the response body and a 200 status code.

-  Upon the Web Server’s request, the SOTA Core can start an Update Packages per VIN Request process and if the given VIN does not exist, it will respond with a ‘VIN does not exist’ message in the response body and a 409 status code.

-  Upon the Web Server’s request, the SOTA Core can start an Update Packages per VIN Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B2-10 Web Server sends View Packages per VIN Request to SOTA Core
--------------------------------------------------------------------

The Web Server can send a View Packages per VIN Request to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the SOTA Core can start a View Packages per VIN Request process and if a request is processed without errors, it will respond with the installed packages on the selected VIN in the response body and a 200 status code.

-  Upon the Web Server’s request, the SOTA Core can start a View Packages per VIN Request process and if the given VIN does not exist, it will respond with a ‘VIN does not exist’ message in the response body and a 409 status code.

-  Upon the Web Server’s request, the SOTA Core can start a Update Package per VIN Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B2-11 Web Server sends View VINs per Package Request to SOTA Core
--------------------------------------------------------------------

The Web Server can send a View VINs per Package Request to SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, SOTA Core can start a View VINs per Package Request Request process and if a request is processed without errors, it will respond with the VINs that have installed the selected Package in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can start a View VINs per Package Request process and if the given Package does not exist, it will respond with a ‘Package does not exist’ message in the response body and a 409 status code.

-  Upon the Web Server’s request, SOTA Core can start a View VINs per Package Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B2-12 Web Server sends new component data to SOTA Core
---------------------------------------------------------

The Web Server can send new a Component to SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, SOTA Core can look for the Component ID lookup process and if the Component exists, it will respond with a ‘Component already created’ message in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can lookup for the Component ID, and if the Component does not exist, it will respond with a ‘Unknown Package ID’ message in the response body and a 404 status code.

WL-B2-13 Web Server sends Search Component Request to SOTA Core
---------------------------------------------------------------

The Web Server can send a Search Components Request to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, SOTA Core can start a Search Components Request process and if a request is processed without errors, it will respond with a list of Components matching the search criteria (regex, ID/IDs or name) in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can start a Search Components Request process and if there are no Components matching the search criteria, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Server’s request, SOTA Core can start a Search Components Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B2-14 Web Server sends "Get Install Request Completed" Request to SOTA Core
------------------------------------------------------------------------------

The Web Server can send a "Get Install Request Completed" to the SOTA Core using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the SOTA Core can send a "Get Install Request Completed" and if the request has been processed without errors, it will respond with a list of VINs where the Install Request was successfully completed and the associated timestamp when the Request was finished in the response body and a 200 response code.

-  Upon the Web Server’s request, the SOTA Core can send a "Get Install Request Completed" and if no VINs have been returned, it will return an empty list in the response body and a 200 response code.

-  Upon the Web Server’s request, the SOTA Core can send a "Get Install Request Completed" and if the request has been processed with errors, it will respond with the apropriate error message in the response body and a 200 response code.

B-3 Web Server - External Resolver
==================================

WL-B3-1 Web Server sends New Package Request to External Resolver
-----------------------------------------------------------------

The Web Server can send a New Package Request to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can start a new Package insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a new Package insertion process and if a database entry already exists, it will respond with a ‘VIN already exists’ message in the response body and a 409 status code.

WL-B3-2 Web Server sends new VIN to External Resolver
-----------------------------------------------------

The Web Server can send new VINs to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can start a new VIN insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a new VIN insertion process and if a database entry already exists, it will respond with a ‘VIN already exists’ message in the response body and a 409 status code.

WL-B3-3 Web Server sends filter-to-package association to External Resolver
---------------------------------------------------------------------------

The Web Server can send a new association between a Filter and a Package to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can start a new Filter/Package association insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a new Filter/Package association insertion process and if a database entry already exists, it will respond with a ‘Filter/Package association already exists’ message in the response body and a 409 status code.

-  Upon the Web Server’s request, the External Resolver can start a new Filter/Package association insertion process and if the Filter does not exist, it will respond with a ‘Filter label does not exist’ message in the response body and a 404 status code.

-  Upon the Web Server’s request, the External Resolver can start a new Filter/Package association insertion process and if the Package does not exist, it will respond with a Package ID does not exist’ message in the response body and a 404 status code.

WL-B3-4 Web Server sends New Filter Request to External Resolver
----------------------------------------------------------------

The Web Server can send a New Filter Request to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can start a new Filter insertion process and if a new database entry is created, it will respond with a ‘Row Inserted’ message in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a new Filter insertion process and if a database entry already exists, it will respond with a ‘Filter already exists’ message in the response body and a 409 status code.

-  Upon the Web Server’s request, the External Resolver can start a new Filter insertion process and if the Filter expression fails validation, it will respond with a ‘Filter failed validation’ message in the response body and a 406 status code.

WL-B3-5 Web Server sends Resolve VIN Request to External Resolver
-----------------------------------------------------------------

Web Server can send a Resolve VIN Request to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can resolve the dependencies for all VINs involved and if the request is processed without errors, it will respond with the subset of all VINs that passed all filters in the response body and a 200 status code.

-  Upon Web Server’s request, the External Resolver can resolve the dependencies for all VINs involved and if no filters are associated with the package, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B3-6 Web Server sends Search Filters Request to External Resolver
--------------------------------------------------------------------

The Web Server can send a Search Filters Request to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can start a Search Filters Request process and if a request is processed without errors, it will respond with a list of Filters matching the search criteria in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a Search Filters Request process and if there are no Filters matching the search criteria, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a Search Filters Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B3-7 Web Server sends List Filters Request to External Resolver
------------------------------------------------------------------

The Web Server can send a List Filters Request to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can start a Search List Request process and if a request is processed without errors, it will respond with a list of available Filters in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a Search Filters Request process and if there are no available Filters, it will respond with an empty list in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a List Filters Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B3-8 Web Server sends Update Filter Request to External Resolver
-------------------------------------------------------------------

The Web Server can send a Update Filter Request to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can start a Update Filter Request process and if a request is processed without errors, it will respond with a list of available Filters in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a Update Filter Request process and if there is no Filter with the given Filter ID, it will respond with a ‘Filter does not exist’ message in the response body and a 409 status code.

-  Upon the Web Server’s request, the External Resolver can start a Update Filter Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

WL-B3-9 Web Server sends Delete Filter Request to Web Server
------------------------------------------------------------

The Web Server can send a Delete Filter Request to the External Resolver using JSON over HTTP on port 80.

-  Upon the Web Server’s request, the External Resolver can start a Delete Filter Request process and if a request is processed without errors, it will respond with a list of available Filters in the response body and a 200 status code.

-  Upon the Web Server’s request, the External Resolver can start a Delete Filter Request process and if there is no Filter with the given Filter ID, it will respond with a ‘Filter does not exist’ message in the response body and a 409 status code.

-  Upon the Web Server’s request, the External Resolver can start a Delete Filter Request process and if an error occurs, it will respond with an error message in the response body and a 404 status code.

B-4 SOTA Core - SOTA Core Database
==================================

WL-B4-1 SOTA Core persists new VIN to SOTA Core Database
--------------------------------------------------------

SOTA Core can persist new VIN data to the SOTA Core Database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new VIN data and if a new database entry is created, it will respond with a ‘Success’ message.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new VIN data and if the VIN already exists, it will respond with a ‘Record exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-2 SOTA Core persists new package data to SOTA Core Database
-----------------------------------------------------------------

SOTA Core can persist new package data to the SOTA Core Database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Package data and if a new database entry is created, it will respond with a ‘Success’ message.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Package data and if the Package already exists, it will respond with a ‘Record exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-3 SOTA Core looks up Package ID in SOTA Core Database
-----------------------------------------------------------

SOTA Core can perform a lookup operation for a Package ID in the SOTAServer database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given Package ID and if an entry is found, it will respond with the Package’s data.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given Package ID and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-4 SOTA Core looks up Updates in SOTA Core Database
--------------------------------------------------------

SOTA Core can perform a lookup operation for an Update in the SOTAServer database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given Update ID and if an entry is found, it will respond with the Package’s data.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given Update ID and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-5 SOTA Core looks up VINs in SOTA Core Database
-----------------------------------------------------

SOTA Core can perform a lookup operation for VINs matching the given criteria in the SOTAServer database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given search criteria and if an entry is found, it will respond with the VINs’ data.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given search criteria and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-6 SOTA Core looks up Packages per VIN in SOTA Core Database
-----------------------------------------------------------------

SOTA Core can perform a lookup operation for the installed Packages on a given VIN in the SOTAServer database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given VIN and if an entry is found, it will respond with Package data associated with the VIN.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given VIN and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-7 SOTA Core updates Packages per VIN in SOTA Core Database
----------------------------------------------------------------

SOTA Core can perform an UPDATE operation for the packages associated with a given VIN in the SOTAServer database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an UPDATE operation with the package data for the selected VIN and if an entry is found, it will respond with the VINs’ data.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given VIN and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-8 SOTA Core looks up VINs per Package in SOTA Core Database
-----------------------------------------------------------------

SOTA Core can perform a lookup operation for the VINs with have installed the Package with the given Package ID in the SOTA Server database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given Package ID and if an entry is found, it will respond with the VINs who have installed the given package.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given Package ID and if no VINs are found, it will respond with a ‘No VINs have this package installed’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-9 SOTA Core persists new Component to SOTA Core Database
--------------------------------------------------------------

SOTA Core can persist new Component data to the SOTA Core Database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Component data and if a new database entry is created, it will respond with a ‘Success’ message.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Component data and if the VIN already exists, it will respond with a ‘Record exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-10 SOTA Core looks up Component ID in SOTA Core Database
--------------------------------------------------------------

SOTA Core can perform a regex-based lookup operation for a Component or Componets in the SOTA Server database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given Component ID and if an entry is found, it will respond with the Package’s data.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given Component ID and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B4-11 SOTA Core looks ip the given Install Request ID in SOTA Core Database
------------------------------------------------------------------------------

SOTA Core can perform a look up operation for a given Install Request ID in the SOAT Server database in the Database Server over TCP on port 3306.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given Install Request ID and if one or more VINs with the a successful installation for the given Install Request ID is found, it will respond with the VINs and the timestamp the installation has been completed data.

-  If SOTA Core authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given Install Request ID and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

B-5 SOTA Core - External Resolver
=================================

WL-B5-1 SOTA Core sends Resolve VIN Request to External Resolver
----------------------------------------------------------------

SOTA Core can send a Resolve VIN request to the External Resolver using JSON over HTTP on port 80.

-  Upon the SOTA Core’s request, the External Resolver can resolve the dependencies for all VINs involved and if the request is processed without errors, it will respond with the subset of all VINs that passed all filters in the response body and a 200 status code.

-  Upon SOTA Core’s request, the External Resolver can resolve the dependencies for all VINs involved and if no filters are associated with the package, it will respond with a ‘No filters associated with package’ message in the response body and a 404 status code.

WL-B5-2 SOTA Core sends install report result code to External Resolver
-----------------------------------------------------------------------

SOTA Core can send the result code of a install report to the External Resolver using JSON over HTTP on port 80.

-  Upon SOTA Core’s request, the External Resolver can update the External Resolver Database with the result of the install report and if the result code is SUCCESS it will update the installed package list and reply with a 'package installed' message in the response body and a 200 status code.

-  Upon SOTA Core’s request, the External Resolver can update the External Resolver Database with the result of the install report and if the result code is not SUCCESS, reply with a 'package installation failed' message in the response body and a 500 status code.

B-6 External Resolver - External Resolver Database
==================================================

WL-B6-1 External Resolver persists new VIN to External Resolver Database
------------------------------------------------------------------------

The External Resolver can persist new VIN data to the External Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new VIN data and if a new database entry is created, it will respond with a ‘Success’ message.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new VIN data and if the VIN already exists, it will respond with a ‘Record exists’ message.

-  If the External Resolver does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-2 External Resolver persists new package data to External Resolver Database
---------------------------------------------------------------------------------

The External Resolver can persist new Package data to the External Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Package data and if a new database entry is created, it will respond with a ‘Success’ message.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Package data and if the Package already exists, it will respond with a ‘Record exists’ message.

-  If the External Resolver does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-3 External Resolver persists new filter data to External Resolver Database
--------------------------------------------------------------------------------

The External Resolver can persist a new Filter to the Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Filter data and if a new database entry is created, it will respond with a ‘Success’ message.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Filter data and if the Filter already exists, it will respond with a ‘Record exists’ message.

-  If the External Resolver does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-4 External Resolver persists filter-to-package association to External Resolver Database
----------------------------------------------------------------------------------------------

The External Resolver can persist a new Filter/Package association to the External Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Filter/Package association and if a new database entry is created, it will respond with a ‘Success’ message.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Filter/Package association and if the Filter/Package association already exists, it will respond with a ‘Record exists’ message.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Filter/Package association and if the Filter does not exist exist, it will respond with a ‘Filter does not exist’ error message.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an INSERT operation with the new Filter/Package association and if the Package does not exist exist, it will respond with a ‘Package does not exist’ error message.

-  If the External Resolver does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-5 External Resolver looks up Package ID filters in External Resolver Database
-----------------------------------------------------------------------------------

The External Resolver can perform a lookup operation for all filters associated with a Package ID to the Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation for all filters associated with the given Package ID and if one or more entries are found, it will respond with the Filters’ data.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation for all filters associated with the given Package ID and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-6 External Resolver looks up VIN in External Resolver Database
--------------------------------------------------------------------

The External Resolver can perform a lookup operation for a VIN to the Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given VIN and if an entry is found, it will respond with the VIN’s data.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given VIN and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-7 External Resolver looks up Package Dependencies in External Resolver Database
-------------------------------------------------------------------------------------

The External Resolver can perform a lookup operation for all the package dependencies of a VIN to the Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given VIN and if an entry is found, it will respond with all the software dependencies for the given VIN data.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given VIN and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-8 External Resolver looks up Filters in External Resolver Database
------------------------------------------------------------------------

The External Resolver can perform a lookup operation for all the Filters to the Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a SELECT operation with the given Filter ID or IDs and if an entry is found, it will respond with all the software dependencies for the given VIN data.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an SELECT operation with the given Filter ID or IDs and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-9 External Resolver updates Filters in External Resolver Database
-----------------------------------------------------------------------

The External Resolver can perform an update operation for one or many Filters to the Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an UPDATE operation with the given Filter ID or IDs and if an entry is found, it will respond with the number of Filters correctly updated.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an UPDATE operation with the given Filter ID or IDs and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-10 External Resolver deletes Filters in External Resolver Database
------------------------------------------------------------------------

The External Resolver can perform a delete operation for one or many Filters to the Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a DELETE operation with the given Filter ID or IDs and if an entry is found, it will respond with the number of Filters deleted.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform a DELETE operation with the given Filter ID or IDs and if no entry is found, it will respond with a ‘Record does not exists’ message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

WL-B6-11 External Resolver updates installed package list for the VIN in External Resolver Database
---------------------------------------------------------------------------------------------------

The External Resolver can perform an update installed package lists operation for one or many VINS to the Resolver database in the Database Server over TCP on port 3306.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an UPDATE operation with the given Package ID on the given VIN or VINs and if an entry is found, it will respond with the number of VINs update.

-  If the External Resolver authenticates successfully with the correct Username/Password credentials, upon its request, the Database Server can perform an UPDATE operation with the given Package ID on the given VIN or VINs and if no entry is found, it will respond with a 'Record does not exists' message.

-  If SOTA Server does not authenticate successfully due to incorrect Username/Password credentials against the Database Server, the Database Server should reject the connection.

B-7 SOTA Core - RVI Node Server
===============================

WL-B7-1 SOTA Core sends Software Update Metadata for VIN to RVI Node Server
---------------------------------------------------------------------------

Core can send a software update [main Package ID, dependent Package IDs to install, date/time interval, priority, creation date/timestamp] for each VIN to the RVI Node using JSON over HTTP on port 80.

-  Upon Core’s request, the RVI Node can schedule the installation of the software packages listed for every given VIN if the task is scheduled without errors, it will respond with the subset of all VINs that passed all filters in the response body and a 200 status code.

-  Upon Core’s request, the RVI Node can schedule the installation of the software packages listed for every given VIN and if any errors occur, it will respond with a ‘Task scheduling’ message in the response body and a 412 status code.

WL-B7-2 SOTA Core sends “Software Update Available” notification to RVI Node Server
-----------------------------------------------------------------------------------

SOTA Core can send “Software Update Available” notifications [Package ID, Size, Download Index, Description] to RVI Node Server using JSON on port 80 over HTTP.

-  Upon SOTA Core’s request, the RVI Node Server can start the software update process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon SOTA Core’s request, the RVI Node Server can start the software update process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server X times to resume the update.

WL-B7-3 RVI Node Server sends “Initiate Software Download” notification to SOTA Core
------------------------------------------------------------------------------------

RVI Node Server can send a “Initiate Software Download” [Download Index] notification to SOTA Core using JSON on port 80 over HTTP.

-  Upon the RVI Node Server’s request, SOTA Core can start the update download process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon the RVI Node Server’s request, SOTA Core can start the update download process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server to resume the update.

-  Upon the RVI Node Server’s “Cancel Software Download” request, SOTA Core can interrupt the update download process.

WL-B7-4 SOTA Core sends “Start Download” notification to RVI Node Server
------------------------------------------------------------------------

SOTA Core can send a “Start Download” notification to RVI Node Server using JSON on port 80 over HTTP.

-  Upon SOTA Core’s request, RVI Node Server can start the download process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon the RVI Node Server’s request, SOTA Core can start the update download process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server to resume the update.

-  Upon the RVI Node Server’s “Cancel Software Download” request, SOTA Core can interrupt the update download process.

WL-B7-5 SOTA Core sends lowest numbered data block to RVI Node Server
---------------------------------------------------------------------

SOTA Core can send the lowest numbered data block to RVI Node Server using JSON on port 80 over HTTP.

-  Upon SOTA Core’s request, RVI Node Server can accept the lowest numbered data block and if the data block is received without errors, it will acknowledge of successful data block receipt in the response body and a 200 status code.

-  Upon SOTA Core’s request, RVI Node Server can accept the lowest numbered data block and if the data block has been received before the data block will be discarded and the next data block will be requested.

-  Upon SOTA Core’s request, RVI Node Server can accept the lowest numbered data block and if the data block is interrupted due to network loss, it will attempt to reconnect X times and transmit again the data block.

WL-B7-6 SOTA Core sends “Finalise Download” notification to RVI Node Server
---------------------------------------------------------------------------

SOTA Core can send a “Finalize Download” notification to RVI Node Server using JSON on port 80 over HTTP.

-  Upon SOTA Core’s request, RVI Node Server can confirm the completion of download process and if the download is finished without errors, it will respond with ‘Download of *Package ID* complete’ in the response body and a 200 status code.

-  Upon SOTA Core’s request, RVI Node Server can confirm the completion of download process and if data blocks are missing, it will respond with ‘Incomplete Download’ in the response body and a 400 status code.

-  Upon the RVI Node Server’s request, SOTA Core can start the update download process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server to resume the update.

WL-B7-7 SOTA Core sends Install Report to RVI Node Client
---------------------------------------------------------

SOTA Client can send an Install Report to RVI Node Client the using JSON on port 80 over HTTP.

-  Upon the SOTA Client’s request, RVI Node Client can accept the Install Report and if the installation was finished without errors, it will respond with ‘\ *Package ID* success’ in the response body and a 200 status code.

-  Upon the SOTA Client’s request, RVI Node Client can accept the Install Report and if the VIN is already marked as complete, it will respond with ‘\ *Package ID* failed’ in the response body and a 409 status code.

-  Upon the SOTA Client’s request, RVI Node Client can accept the Install Report and if the VIN is already marked as failed, it will respond with ‘\ *Package ID* failed’ in the response body and a 409 status code.

WL-B7-8 SOTA Core sends a "Get All Packages" notification to RVI Node Server
----------------------------------------------------------------------------

SOTA Core can send a "Get All Packages" notification to RVI Node Server the using JSON on port 80 over HTTP.

-  Upon the SOTA Core’s request, RVI Node Server can accept a "Get All Packages" request and if it has been responded without errors, it will respond with a list of installed package IDs in the response body and a 200 status code.

-  Upon the SOTA Core’s request, RVI Node Server can accept a "Get All Packages" request if the response contained errors, it will respond with an error message in the response body and a 409 status code.

B-8 RVI Node Server - RVI Node Client
=====================================

WL-B8-1 RVI Node Server sends “Software Update Available” notification to RVI Node Client
-----------------------------------------------------------------------------------------

RVI Node Server can send “Software Update Available” notifications [Package ID, Size, Download Index, Description] to RVI Node Client.

WL-B8-2 RVI Node Server sends “Start Download” notification to RVI Node Client
------------------------------------------------------------------------------

RVI Node Server can send a “Start Download” notification to RVI Node Client.

WL-B8-3 RVI Node Server sends lowest numbered data block to RVI Node Client
---------------------------------------------------------------------------

RVI Node Server can send the lowest numbered data block to RVI Node Client.

WL-B8-4 RVI Node Server sends “Finalise Download” notification to RVI Node Client
---------------------------------------------------------------------------------

RVI Node Server can send a “Finalize Download” notification to RVI Node Client.

WL-B8-5 RVI Node Client sends Install Report to RVI Node Server
---------------------------------------------------------------

RVI Node Client can send an Install Report to the RVI Node Server.

WL-B8-6 RVI Node Client sends “Initiate Software Download” notification to RVI Node Server
------------------------------------------------------------------------------------------

The RVI Node Client can send “Initiate Software Download” notification to RVI Node Server.

WL-B8-7 RVI Node Server sends a "Get All Packages" notification to RVI Node Client
----------------------------------------------------------------------------------

B-9 RVI Node Client - SOTA Client
=================================

WL-B9-1 RVI Node Client sends “Software Update Available” notification to SOTA Client
-------------------------------------------------------------------------------------

RVI Node Client can send “Software Update Available” notifications [Package ID, Size, Download Index, Description] to SOTA Client the using JSON on port 80 over HTTP.

-  Upon the RVI Node Clients’s request, the SOTA Client can start the software update process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon the RVI Node Client’s request, the SOTA Client can start the software update process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server X times to resume the update.

WL-B9-2 RVI Node Client sends “Initiate Software Download” notification to RVI Node Server
------------------------------------------------------------------------------------------

The RVI Node Client can send “Initiate Software Download” notification to RVI Node Server.

WL-B9-3 RVI Node Client sends “Start Download” notification to SOTA Client
--------------------------------------------------------------------------

RVI Node Client can send a “Start Download” notification to SOTA Client using JSON on port 80 over HTTP.

-  Upon RVI Node Client’s request, SOTA Client can start the download process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon the RVI Node Client’s request, SOTA Client can start the update download process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server to resume the update.

-  Upon the RVI Node Client’s “Cancel Software Download” request, SOTA Client can interrupt the update download process.

WL-B9-4 RVI Node Client sends lowest numbered data block to SOTA Client
-----------------------------------------------------------------------

RVI Node Client can send the lowest numbered data block to SOTA Client using JSON on port 80 over HTTP.

-  Upon RVI Node Client’s request, SOTA Client can accept the lowest numbered data block and if the data block is received without errors, it will acknowledge of successful data block receipt in the response body and a 200 status code.

-  Upon RVI Node Client’s request, SOTA Client can accept the lowest numbered data block and if the data block has been received before the data block will be discarded and the next data block will be requested.

WL-B9-5 RVI Node Client sends “Finalize Download” notification to SOTA Client
-----------------------------------------------------------------------------

RVI Node Client can send a “Finalize Download” notification to SOTA Client using JSON on port 80 over HTTP.

-  Upon RVI Node Client’s request, SOTA Client can confirm the completion of download process and if the download is finished without errors, it will respond with ‘Download of *Package ID* complete’ in the response body and a 200 status code.

-  Upon RVI Node Client’s request, SOTA Client can confirm the completion of download process and if data blocks are missing, it will respond with ‘Incomplete Download’ in the response body and a 400 status code.

-  Upon the RVI Node Client’s request, SOTA Client can start the update download process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server to resume the update.

WL-B9-6 SOTA Client sends Install Report to RVI Node Client
-----------------------------------------------------------

SOTA Client can send an Install Report to RVI Node Client the using JSON on port 80 over HTTP.

-  Upon the SOTA Client’s request, RVI Node Client can accept the Install Report and if the installation was finished without errors, it will respond with ‘\ *Package ID* success’ in the response body and a 200 status code.

-  Upon the SOTA Client’s request, RVI Node Client can accept the Install Report and if the VIN is already marked as complete, it will respond with ‘\ *Package ID* failed’ in the response body and a 409 status code.

-  Upon the SOTA Client’s request, RVI Node Client can accept the Install Report and if the VIN is already marked as failed, it will respond with ‘\ *Package ID* failed’ in the response body and a 409 status code.

WL-B9-7 RVI Node Client sends a "Get All Packages" notification to SOTA Client
------------------------------------------------------------------------------

RVI Node Client can send a "Get All Packages" notification to SOTA Client the using JSON on port 80 over HTTP.

-  Upon the RVI Node Client’s request, SOTA Client can accept the "Get All Packages" notification and if it can retrieve all package information without errors, it will respond with a list of installed Package IDs in theresponse body and a 200 status code.

-  Upon the RVI Node Client’s request, SOTA Client can accept the "Get All Packages" notification and if an error occurs, it will respond with the appropriate error code in the response body and a 409 status code.

B-10 SOTA Client - Software Loading Manager
===========================================

WL-B10-1 SOTA Client sends “Software Update Available” notification to Software Loading Manager
-----------------------------------------------------------------------------------------------

SOTA Client can send “Software Update Available” notifications [Package ID, Size, Download Index, Description] to Software Loading Manager using JSON on port 80 over HTTP.

-  Upon SOTA Clients’s request, Software Loading Manager can start the software update process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon the SOTA Clients’s request, Software Loading Manager can start the software update process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server X times to resume the update.

WL-B10-2 Software Loading Manager sends “Initiate Software Download” notification to SOTA Client
------------------------------------------------------------------------------------------------

Software Loading Manager can send a “Initiate Software Download” [Download Index] notification from to SOTA Client using JSON on port 80 over HTTP.

-  Upon the Software Loading Manager’s request, SOTA Client can start the update download process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon the Software Loading Manager’s request, SOTA Client can start the update download process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server to resume the update.

-  Upon the Software Loading Manager’s “Cancel Software Download” request, SOTA Client can interrupt the update download process.

WL-B10-3 SOTA Client sends “Initiate Software Download” notification to Software Loading Manager
------------------------------------------------------------------------------------------------

SOTA Client can accept a “Initiate Software Download” [Download Index] notification to Software Loading Manager using JSON on port 80 over HTTP.

-  Upon the SOTA Client’s request, Software Loading Manager can start the update download process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon the SOTA Client’s request, Software Loading Manager can start the update download process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server to resume the update.

-  Upon the SOTA Client’s “Cancel Software Download” request, Software Loading Manager can interrupt the update download process.

WL-B10-4 SOTA Client sends “Start Download” notification to Software Loading Manager
------------------------------------------------------------------------------------

SOTA Client can send a “Start Download” notification to Software Loading Manager using JSON on port 80 over HTTP.

-  Upon SOTA Client’s request, Software Loading Manager can start the download process and if the update is finished without errors, it will respond with ‘Installation of *Package ID* complete’ in the response body and a 200 status code.

-  Upon the SOTA Client’s request, Software Loading Manager can start the update download process and if the update is interrupted due to lost network, it will try to reconnect to RVI Node Server to resume the update.

-  Upon the SOTA Client’s “Cancel Software Download” request, Software Loading Manager can interrupt the update download process.

WL-B10-5 SOTA Client sends lowest numbered data block to Software Loading Manager
---------------------------------------------------------------------------------

SOTA Client can send the lowest numbered data block to Software Loading Manager using JSON on port 80 over HTTP.

-  Upon SOTA Client’s request, Software Loading Manager can accept the lowest numbered data block and if the data block is received without errors, it will acknowledge of successful data block receipt in the response body and a 200 status code.

-  Upon SOTA Client’s request, Software Loading Manager can accept the lowest numbered data block and if the data block has been received before the data block will be discarded and the next data block will be requested.

WL-B10-6 Software Loading Manager sends Install Report to SOTA Client
---------------------------------------------------------------------

Software Loading Manager can send an Install Report to SOTA Client the using JSON on port 80 over HTTP.

-  Upon the Software Loading Manager’s request, SOTA Client can accept the Install Report and if the installation was finished without errors, it will respond with ‘\ *Package ID* success’ in the response body and a 200 status code.

-  Upon the Software Loading Manager’s request, SOTA Client can accept the Install Report and if the VIN is already marked as complete, it will respond with ‘\ *Package ID* failed’ in the response body and a 409 status code.

-  Upon the Software Loading Manager’s request, SOTA Client can accept the Install Report and if the VIN is already marked as failed, it will respond with ‘\ *Package ID* failed’ in the response body and a 409 status code.

WL-B10-7 SOTA Client sends a "Get All Packages" notification to Software Loading Manager
----------------------------------------------------------------------------------------

SOTA Client can send an Install Report to Software Loading Manager the using JSON on port 80 over HTTP.

-  Upon the SOTA Client’s request, Software Loading Manager can accept the "Get All Packages" notification and if no error occurs, it will respond with a list of installed Package IDs in the response body and a 200 status code.

-  Upon the SOTA Client’s request, Software Loading Manager can accept the "Get All Packages" notification and if an error occurs, it will reply with the appropriate error code in the response body and a 409 status code.

B-11 Charging & Billing API - SOTA Core
=======================================

B-12 Logistics & Provisioning API - SOTA Core
=============================================

B-13 Web Server - Physical Package Repository / Filesystem
==========================================================

WL-B13-1 Web Server sends the binary package to Filesystem
----------------------------------------------------------

The Web Server can upload a binary package to the Filesystem.

-  Upon the Web Server’s request, the Filesystem can write a binary package if the request is processed without errors, it will respond with a success error code.

-  Upon the Web Server’s request, the Filesystem can write a binary package if the request is processed with errors, it will respond with a failure error code.

.. |Whitelisted Interactions Reference Diagram| image:: ../images/Whitelisted-Interactions-Reference-Diagram.svg

