# pbo_fproject_predator_prey

inspired by: https://www.youtube.com/watch?v=qwrp3lB-jkQ&t=19s
<br>( Evolving AIs - Predator vs Prey, who will win? ) by Pezzza's Work | No Source Code
<br>Created/Coded by Yoel Mountanus Sitorus 
<br>NRP 5025211078 Informatics Engineering of ITS
<br>Youtube Link: https://youtu.be/v-V8bw-OowE

<img width="590" alt="image" src="https://user-images.githubusercontent.com/27951856/207224325-4d957f0f-6f2b-459a-8653-ac9d8c8afc41.png">

There are 3 entitities that are Predator, Prey, and Foliage
1. Foliage ( NOPE )
   Foliage will be static entities that can't be move / grows at a steady pace
   it will be eaten by preys, and neutral to the predators. Grow in random place ( no time to make this )

2. Prey
   Preys are eaten by Predator. Prey have energy but not depleted overtime.
   it will decrease when the prey is moving. If the energy is drop to zero, they will got a children.
   they life long enough, and can split overtime.

3. Predator
   Predators have an energy that is depleted overtime, and if their energy drops to zero, they will die.

Coded a Genetic Algorithm with simple case
To determine the best gen / best entity I assumed that the longest life entity that have the best neural network.
<br /> that implement natural selection that the best gen left.

Implemented OOP
- Casting/Conversion
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/Collection/Coordinate.java#L40-L53
- Constructor
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/entities/Predator.java#L10-L18
- Overloading
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/Collection/Coordinate.java#L12-L20
- Overriding
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/Collection/Coordinate.java#L12-L20
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/App.java#L100-L117
- Encapsulation
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/entities/Entities.java#L91-L106
- Inheritance
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/entities/Prey.java#L8
- Polymorphism
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/App.java#L16
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/App.java#L90-L98
- ArrayList
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/genetic/Genome.java#L5-L7
- Exception Handling
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/App.java#L60-L68
- GUI
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/App.java#L24-L37
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/App.java#L75-L86
- Generics
[Coordinate.java](/src/Collection/Coordinate.java)
- Collection
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/genetic/Genome.java#L5-L7
- Input Output
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/App.java#L29-L37
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/App.java#L105-L117
- Implements Interface
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/entities/Predator.java#L8
- Abstract Class
https://github.com/zemetia/pbo_fproject_predator_prey/blob/7ac7c8629f4865d87c636e606b84d581d6edc9d0/src/entities/Entities.java#L11-L14

