CocoR-Practices
===============

A couple of examples in order to create a parser/compiler for DSLs thanks the effectiveness of [Coco/R] [1]
  [1]:http://www.ssw.uni-linz.ac.at/Coco/ "Coco/R"

Coco/R is a compiler compiler used to generate a compiler (or a simple parser) relative to the grammar of a programming language or DSL.
For basic purposes, only one .ATG file has to be provided for the generation.  



####First example

Binary trees are dynamic data structures consisting of nodes, where every node has at 
most 2 sons, which are again binary trees. 
 
<pre>
            TREE                  Data Structure               TEXT INPUT                
            London                                            (London
           /      \               class Node{                     (Brussels)  
       Brussels  Paris                String name;                (Paris
                 /   \                Node left;                      (Madrid)
            Madrid  Rome              Node rigth;                     (Rome
                       \          }                                       ()
                     Vienna                                               (Vienna)
                                                                      )
                                                                  )
                                                              )
</pre>

The aim is to parse the input and format the text in a .xml file. Node without child should be represented by an empty element.


####Second example

A text file 

