Database Style Guide
````````````````````

Table names are UpperCamelCase Singular.
========================================

Table names should be the same as the Scala domain object that represents them (if it exists). By using the same casing rules as Scala, the domain object and SQL table names can match exactly.

For more arguments on the singular/plural naming, see http://stackoverflow.com/questions/338156/table-naming-dilemma-singular-vs-plural-names

Column names are lowerCamelCase
===============================

Lower camel case column names should match scala property names.

Surrogate primary keys are called 'id'.
=======================================

For example:

.. code:: sql

        -- Good
        CREATE TABLE Person (
          id int PRIMARY KEY,
          ...
        );

rather than:

.. code:: sql

        -- BAD
        CREATE TABLE Person (
          personId int PRIMARY KEY, -- BAD: should be 'id'
          ...
        );
