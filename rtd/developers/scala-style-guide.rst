Scala Style Guide
`````````````````

The document contains Scala stylistic guidelines, which should be fillowed in order to produce readable and idiomatic code, allowing effective collaboration. The following style guides were used as a basis:

1. `Typesafe Scala Style Guide <http://docs.scala-lang.org/style/>`__

2. `Databricks Scala Guide <https://github.com/databricks/scala-style-guide>`__

3. `Twitter’s Effective Scala <http://twitter.github.io/effectivescala/>`__

Syntactic Style
===============

Indentation
-----------

-  General indentation: 2 spaces

-  Limit lines to 100 characters

-  If a single expression is longer than 100 characters it should be spitted into multiple expressions by assigning intermediate results to values. In case it is not practical and the expression should be wrapped across several lines, each successive line should be indented two spaces from the first.

-  When calling a method which takes numerous arguments (in the range of five or more), it is often necessary to wrap the method invocation onto multiple lines. In such cases, put each argument on a line by itself, indented two spaces from the current indent level:

   .. code:: scala

       foo(
         someVeryLongFieldName,
         andAnotherVeryLongFieldName,
         "this is a string",
         3.1415)

Naming Convention
-----------------

-  Classes, traits should follow Java class convention, i.e. CamelCase style with the first letter capitalized.

   .. code:: scala

       class RedFox

       trait LazyDog

-  Objects follow the class naming convention except when attempting to mimic a package or a function.

   .. code:: scala

       object ast {
         sealed trait Expr
         case class Plus(e1: Expr, e2: Expr) extends Expr
         ...
       }

       object inc {
         def apply(x: Int): Int = x + 1
       }

-  Scala packages should follow the Java package naming conventions

   .. code:: scala

       package com.databricks.resourcemanager

-  Constant names should be in upper camel case. That is, if the member is final, immutable and it belongs to a package object or an object, it may be considered a constant (similar to Java’s static final members):

   .. code:: scala

       object Foo {
         val Bar = 10
       }

-  Enums should be CamelCase with first letter capitalized.

-  Names for methods should be in the camelCase style with the first letter lower-case.

Declarations
------------

All guides referenced above agreed on how the declarations should be styled. Please refer to the one from `Scala Style Guide <http://docs.scala-lang.org/style/declarations.html>`__, as is most comprehensive of them.

Imports
-------

-  Do NOT use wildcard imports, unless you are importing implicit methods.

-  Always import packages using absolute paths

Control Structures
------------------

Control structures should be styled as defined in `Scala Style Guide <http://docs.scala-lang.org/style/control-structures.html>`__

Infix Methods
-------------

Do NOT use infix notation for methods that aren’t symbolic methods (i.e. operator overloading).

.. code:: scala

    // Correct
    list.map(func)
    string.contains("foo")

    // Wrong
    list map (func)
    string contains "foo"

    // But overloaded operators should be invoked in infix style
    arrayBuffer += elem

Documentation Style
-------------------

-  Use Java docs style instead of Scala docs style.

   .. code:: scala

       /**
         * Style mandated by "Scala Style Guide"
         */

       /**
        * Style to use
        */

-  Do not use @author tags since it does not encourage Collective Code Ownership.

Language Features
=================

override Modifier
-----------------

Always add override modifier for methods, both for overriding concrete methods and implementing abstract methods. The Scala compiler does not require ``override`` for implementing abstract methods.

Destructuring Binds
-------------------

Destructuring bind (sometimes called tuple extraction) is a convenient way to assign two variables in one expression.

.. code:: scala

    val (a, b) = (1, 2)

However, do NOT use them in constructors, especially when ``a`` and ``b`` need to be marked transient. The Scala compiler generates an extra Tuple2 field that will not be transient for the above example.

.. code:: scala

    class MyClass {
      // This will NOT work because the compiler generates a non-transient Tuple2
      // that points to both a and b.
      @transient private val (a, b) = someFuncThatReturnsTuple2()
    }

Symbolic Methods (Operator Overloading)
---------------------------------------

Avoid symbolic method names, unless you are defining them for natural arithmetic operations (e.g. ``+``, ``-``, ``*``, ``/``) or as part as some DSL (``!`` to send message in Akka, ``\`` to concatenate parts of a path or an uri). In second case they should be defined as aliases to the non-symbolic functions.

Type Inference
--------------

Scala type inference, especially left-side type inference and closure inference, can make code more concise. That said, there are a few cases where explicit typing should be used:

-  **Public methods should be explicitly typed**, otherwise the compiler’s inferred type can often surprise you.

-  **Implicit methods should be explicitly typed**, otherwise it can crash the Scala compiler with incremental compilation.

-  **Variables or closures with non-obvious types should be explicitly typed**. A good litmus test is that explicit types should be used if a code reviewer cannot determine the type in 3 seconds.

Return Statements
-----------------

**Do NOT use return statements**.

Recursion and Tail Recursion
----------------------------

Do NOT use recursion, unless the problem can be naturally framed recursively (e.g. graph traversal, tree traversal).

For functions that are meant to be tail recursive, apply ``@tailrec`` annotation to make sure the compiler can check it is tail recursive (you will be surprised how often seemingly tail recursive code is actually not tail recursive due to the use of closures and functional transformations.)

Exception Handling
------------------

-  Do NOT catch Throwable or Exception. Use ``scala.util.control.NonFatal``:

   .. code:: scala

       try {
         ...
       } catch {
         case NonFatal(e) =>
           // handle exception; note that NonFatal does not match InterruptedException
         case e: InterruptedException =>
           // handle InterruptedException
       }

Options
-------

-  Use ``Option`` when the value can be empty. Compared with ``null``, an ``Option`` explicitly states in the API contract that the value can be ``None``.

-  When constructing an ``Option``, use ``Option`` rather than ``Some`` to guard against ``null`` values.

   .. code:: scala

       def myMethod1(input: String): Option[String] = Option(transform(input))

       // This is not as robust because transform can return null, and then
       // myMethod2 will return Some(null).
       def myMethod2(input: String): Option[String] = Some(transform(input))

-  Do not use ``None`` to represent exceptions.

-  Do not call ``get`` directly on an ``Option``.

Monadic Chaining
----------------

One of Scala’s powerful features is monadic chaining. Almost everything (e.g. collections, Option, Future, Try) is a monad and operations on them can be chained together. This is an incredibly powerful concept, but chaining should be used sparingly. In particular:

-  Do NOT chain (and/or nest) more than one ``flatMap`` operations.

-  If you need to chain more than one ``flatMap``, use for-comprehension.

Concurrency
-----------

Use Akka for concurrency.

Private Fields
--------------

Note that ``private`` fields are still accessible by other instances of the same class, so protecting it with ``this.synchronized`` (or just ``synchronized``) is not technically sufficient. Make the field ``private[this]`` instead.

.. code:: scala

    // The following is still unsafe.
    class Foo {
      private var count: Int = 0
      def inc(): Unit = synchronized { count + 1 }
    }

    // The following is safe.
    class Foo {
      private[this] var count: Int = 0
      def inc(): Unit = synchronized { count + 1 }
    }

Default Parameter Values
------------------------

Do NOT use default parameter values. Overload the method instead.

.. code:: scala

    // Breaks Java interoperability
    def sample(ratio: Double, withReplacement: Boolean = false): RDD[T] = { ... }

    // The following two work
    def sample(ratio: Double, withReplacement: Boolean): RDD[T] = { ... }
    def sample(ratio: Double): RDD[T] = sample(ratio, withReplacement = false)
