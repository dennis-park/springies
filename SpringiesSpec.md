Springies Specifications
=====

Names: 
* Thanh-Ha Nguyen (tn32)
* Dennis Park (djp28)

Packages & Classes
-----

### Springies Package

#### Main.java
*Instantiates Simulation & JGame GUI* 

#### Simulation 
*Control state of simulation (run, stop, clear)* 

### Controller Package

#### SpringiesController 
Behaviors:
* Modifies environment and objects
* Instantiates SpringiesListener for user input, else default
* Instantiates and sets SpringiesEnvironment
* Instantiates SpringiesAssembler 
* Have knowledge of all objects (Spring, Mass, Muscle) in environment

#### SpringiesEnvironment
Features include: 
* gravity, gravity_direction, viscosity    
  
Behaviors:  
* Controlled by user input      

#### SpringiesListener 
Behaviors:  
* "Listens" for __runtime__ user input (including files)
* Created by SpringiesController

#### SpringiesAssembler
Behaviors: 
* Build Assembly based on either XML input or user input
* May instantiate XML reader (if needed)
* Instantiate Spring, Mass, Muscle objects 

#### XMLReader 
Behaviors: 
* Will parse XML assembly document  

### SpringiesObjects Package

#### Spring    
Features include:     
* springiness    
* connected mass objects

Behaviors:  
* Affect position of mass objects on either side
* Grow/shrink according to environment & spring features
* Change position according to mass objects  
* Spring features/properties are set by SpringiesAssembler & modified 
by controller

#### Muscle
Should extend Spring     
      
Features include: 
* amplitude, speed    
           
Behaviors: 
* Grow/shrink according to environment, spring features, AND muscle 
features (i.e. amplitude/speed)
* Still changes position based on mass objects   
* Affect position of mass objects on either side

#### Mass
Features:   
* weight, size, color, position         
* connected springs (0+ springs)

Behaviors:  
* Can change position based on user input 
* Position also changed by Environment (gravity, viscosity, etc), spring/muscle
movement    

#### Assembly   
*Possibly extend mass? This will allow different assemblies to work 
in one world and interact if ever necessary (extensible -- not for 
this project)*   

Behaviors:        
* Control position of spring-mass assembly as a whole   
* Move as an assembly as opposed to individual masses  
* Affected by Environment (gravity, viscosity, etc)  
* Controlled by user input  
  
### JGame

### JBox
