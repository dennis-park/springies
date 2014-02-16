springies
=========

Authors:
Thanh-ha Nguyen (tn32)
Dennis Park (djp28)

##Project details
Start date: Jan 18 2014

End date: Feb 08 2014

Time spent: 40-50 hrs

[Github repo] (https://github.com/duke-compsci308-spring2014/springies_team24.git)


##What is Springies?
A sandbox simulation manipulating spring-mass assemblies.  Objects are enacted by various forces; affected by user input.  XML docs are parsed and processed, its products loaded into the environment.  The Springies engine contains the list of assemblies, initializes JGame settings, environment, forces, and listeners.

Based off of [Sodaplay Constructor] (http://sodaplay.com/creators/soda/items/constructor).


##Primary roles
Thanh-ha : debugging, xml (Parsers package), forces package, Mass, Walls, setup for Environment

Dennis   : physics, forces package, Assembly, AssemblyFactory, Spring, Muscle, toggling Environment

Both     : extensive refactoring, listeners

##Resources
* Kevin Oh (TA)
* Robert Duvall
* Javadocs
* Stackoverflow
* Stackexchange
* Piazza
* [Sodaplay] (http://sodaplay.com/)

##Starter files
* Main.java
* PhysicalObject.java
* PhysicalObjectCircle.java
* PhysicalObjectRect.java
* WorldManager.java
* JGame Engine
* JBox2D Engine

##How to play
Keystroke : Behavior

'g'       : toggles gravity force

'v'       : toggles viscosity force

'm'       : toggles center of mass force
'1'-'4'   : toggle wall repulsion forces

'c'       : clear all loaded assemblies

'n'       : load new assembly

'Up'      : increase walled canvas

'Down'    : decrease walled canvas

'Leftclick: drag and release assembly (exert spring force)

##Known bugs
* Masses may get stuck in walls due to bounds limitations of JBox and JGEngine

* JFileChooser returns NullPointerException when called (temporary fix: place cursor over top-right corner, then press 'n')
