### EndGame Logical Agent Map and Situation Sequence Generator
A Generator for the EndGame agent, generates the grid and the situation sequence.
### Implementation
The Generator generates a grid orientation in size mxn where both are input for the genGrid method.<br>
The printGrid function prints the grid generated for the programmer to see and inspect.<br>
The solver function solves the grid that was generated as a search problem where it is traversed in BFS.
### Testing
    1) Copy the grid orientation generated and the situation sequence.
    2) Generate your KB for the agent from your fact generator implemented.
    3) Input Snapped(<Situation Squence>) that was generated, your program should return true.
### Example
    Generated Grid : 5,5;1,1;2,0;4,2,0,2,3,4,2,3
    Solved Grid Situation Sequence : result(snap,result(left,result(left,result(up,result(up,result(collect,result(left,result(left,result(down,result(collect,result(down,result(right,result(collect,result(down,result(down,result(right,result(collect,result(right,result(up,s0)))))))))))))))))))
    1) After genrating the grid and the solution,
    2) Open SWIProlog,
    3) consult your file, 
    4) and check for the correctness of the implementation by querying the solution as shown.
    
    Prolog Terminal : 
    ?- Snapped(result(snap,result(left,result(left,result(up,result(up,result(collect,result(left,result(left,result(down,result(collect,result(down,result(right,result(collect,result(down,result(down,result(right,result(collect,result(right,result(up,s0)))))))))))))))))))).
       true.


<br>
Good Luck!
