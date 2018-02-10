Refactoring Discussion:

Reduced some logic expression (mainly for the adjacency-checking methods for each shape of cells).

Eliminated some magic constants and assign them variables.

Eliminated passing lists in public methods (XML reader class passes a list of parameters to simulation class, changed to make it unmodifiable).

Eliminated some unused variables. 

Pulled out sub-methods from large methods to make the code more readable and ensure the unique task of each method.

Resolved errors in float number equality checks (mainly for the adjacency-checking methods for each shape of cells). 

