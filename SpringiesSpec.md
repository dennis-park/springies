Springies Specifications
=====

Names: 
* Thanh-Ha Nguyen (tn32)
* Dennis Park (djp28)

Packages & Classes
-----

### JGame

### JBox

### Springies

#### SpringiesListener 
Behaviors: 
* "Listens" for user input
* Calls controller methods

#### SpringiesController 
Behaviors:
* Modifies environment and objects
* Parse XML files and build the assembly
* Controlled by user input
  
#### Environment
Features include: 
* gravity, gravity_direction, viscosity
Behaviors:
* Controlled by user input  

### SpringiesObjects

#### Spring 
Features include: 
* springiness
Behaviors:
* Controlled by user input & spring features

#### Muscle
Should extend Spring
Extended features: 
* springiness  
Features include: 
* amplitude, speed
Behaviors:
* Controlled by user input & muscle features

#### Mass
Features: 
* weight, size, color, position
Behaviors:
* Can change position 
* Affected by Environment (gravity, viscosity, etc)
* Controlled by user input

#### SpringMassAssembly 
Behaviors: 
* Control position of spring-mass assembly as a whole 
* Move as an assembly as opposed to individual masses
* Affected by Environment (gravity, viscosity, etc)
* Controlled by user input
  
Possibly extend mass? This will allow different assemblies to work 
in one world and interact if ever necessary (extensible -- not for 
this project) 