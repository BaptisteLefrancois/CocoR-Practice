CocoR-Practice
===============

A couple of examples in order to create a parser/compiler for DSLs thanks the effectiveness of [Coco/R] [1]
  [1]:http://www.ssw.uni-linz.ac.at/Coco/ "Coco/R"

Coco/R is a compiler compiler used to generate a compiler (or parser in simple case) relative to the grammar of a programming language or DSL.



####First example

Binary trees are dynamic data structures consisting of nodes, where every node has at 
most 2 sons, which are again binary trees. 
 
<pre>
            TREE                  Data Structure               TEXT INPUT                
            London                                            (London
           /      \               class Node{                     (Brussels)  
       Brussels  Paris                String name;                (Paris
                 /   \                Node left;                      (Madrid)
            Madrid  Rome              Node right;                     (Rome
                       \          }                                       ()
                     Vienna                                               (Vienna)
                                                                      )
                                                                  )
                                                              )
</pre>

The aim is to parse the input and format the text in a .xml file. Node without child should be represented by an empty element.


####Second example

We assume the following text file as a set of phone book entries.
<pre>
  Boulder, John M. 
    home 020 7815 1234 
    office 020 3465 234 
  Brown, Cynthia 1234567 
  Douglas, Ann Louise 
    office +44 (0)20 234 567 
    mobile +43 (0)664 7865 234 
 ... 
</pre>

The grammar must respect the rules:

+ Names consist of letters and may be abbreviated with a dot.
+ Phone numbers consist of an optional country code (e.g. +44), an optional city code (e.g. 020) and a phone number consisting of one or several digit sequences. Country codes start with a '+' and must be followed by a city code (with a '0' in brackets). City codes without country codes start with a '0'. If there is no country code the default is +44. If there is no city code the default is 020.
+ Phone numbers may be preceded by the words "home", "office" or "mobile". If such a word is missing the default is "home". 


 
