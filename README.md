# Discord Bot (Discontinued)
 currentBot version: **Test_4.1** (last larger test push)
* Newest addition: chat based interpreter / parser for my custom programming language disclawd. 
For concrete information check under the Heading "Custom Language"
>Disclaimer : This Bot was created with the intention to track my own university assignments and their points. As you see
> this somehow turned into a whole Project, as learning experience for me. **If** wished I will and can provide you with 
> the invite link for the Bot. Contact via Discord (Username: kekmax).


### Features

1. Info Commands
   * ```/bot-info``` - all bot information you need
   * ```/command-info``` - all command information you need
    

2. Setter Commands
   * ```/set-reminder``` - remind yourself to do your stuff
   * ```/set-test``` - insert your test with all information
   * ```/set-todo``` - set a todo


3. Getter Commands
   * ```/get-closest-assignment``` - watch out, it's close!
   * ```/get-tests-information``` - returns if you passed the tests overall
   * ```/get-test-types``` - returns all test types you entered
   * ```/get-todos``` - returns all your todo entries

***

### Special commands

* ```/set-bot-status``` - sets bot status - ***!!!can only be used with a specific UserID!!!***
* ```/clean-up-reminder``` - deletes all dates in the past - ***!!!can only be used with a specific UserID!!!***

1. Data manipulation
  * ```/delete-todo``` - deletes a todo entry by id

***

### Custom Language (disclawd)

* **Rules:**
```
FunctionExpression f := func name var_1, var_2, ... var_i def e
   name -> "any letter combination[A-Za-z]"
   var  -> "any letter combination[A-Za-z]"
   
Expression e := ArithmeticExpression a
              | IfExpression if
              
ArithmeticExpression a := op ae ae
   op -> ADD | SUB | MUL | DIV | MOD
   ae -> ArithmeticExpression a
      | Interger.min - Interger.max
      | var

IfExpression if := if con then a else a
   con -> ae cop ae
      | Interger.min - Interger.max
      | var
   ae -> ArithmeticExpression a
      | Interger.min - Interger.max
      | var
   cop -> < | > | = | <= | >=
   
(disclaimer im not sure if this is the correct difinition so far could be vary according to the development state)
```
- **Evaluating expressions:** The interpreter is able to interpret expressions without function
definitions! Example:
    - ``if 3 < 5 then 3 else 5`` would result in ``3``
    - ``add 3 4`` would result in ``7``
    - if a variable is used within such expression it is valued as **!!!** 0 **!!!**
    - add 3 a would result in ``3``
  
  
- **Function definitions:** To define a function you have to use the ``func . . . def . . .`` structure! Example: 
   - ``func min a b def if a < b then a else b`` would be the definition for a minimum function
   - defining the function again ``func min a b def if a <= b then a else b`` will overwrite the old one.
  

- **Calling functions:** To call a function successfully declared by you, you can use ```call```! Example:
  - ``call min 3 4`` would result in 3


- **Function examples:**
  - ``func min a b def if a <= b then a else b``
  - ``func max a b def if a >= b then a else b``
  - ``func iseven a def if mod a 2 = 0 then 1 else 0`` 

***

### Features planed / in work

* [x] Custom language: adding the ``!=`` operator
* [x] Custom language: adding nested IfExpressions
* [x] Custom language: adding ``while ... do ... `` loop


* [x] study timer
* [x] group organization
* [x] fun commands for breaks
* [x] ! basic math and coding !
* [ ] use of APIs?

The plan now? Preparing last bits of code for an Alpha release version of the bot and testing, testing and testing features.

> If you got this far and read all these points, feel free to step into contact with me for further details or even feedback 
> as you like, i´m happy to hear anything.
