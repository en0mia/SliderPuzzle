<div align="center">

<h3 align="center">SliderPuzzle</h3>

  <p align="center">
    A* Java implementation to solve the N-Puzzle problem.
    <br />
    Solution for the <a href="https://www.coursera.org/learn/algorithms-part1">Sedgewich's Algorithms Course</a> assignment for the week 4.
    <br />
  </p>
</div>

<!-- ABOUT THE PROJECT -->
## About The Project

While I was studying for my Algorithms course I faced this fascinating assignment and I decided to share my implementation of the A* algorithm.

I firstly implemented it following the course's requirements (with their own libraries and implementations), then I decided to refactor my code in order to use the standard Java libraries.
<p align="right">(<a href="#top">back to top</a>)</p>

### Prerequisites

* You can read something about the A* algorithm [here](https://en.wikipedia.org/wiki/A*_search_algorithm).

### Under the hood

#### Unsolvable boards detection
There is an interesting property of this algorithm which states that boards are divided into two subsets:
* Those that can lead to the goal board
* Those that can lead to the goal board if we modify the initial board by swapping any pair of tiles (using a twin board).

In order to detect an unsolvable board, we can run the algorithm both on the initial board and its twin, maintaining two different queues.

If the twin board leads to a solution, then the initial board is unsolvable.

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Simone Nicol - [@en0mia](https://twitter.com/en0mia)

Project Link: [https://github.com/en0mia/SliderPuzzle](https://github.com/en0mia/SliderPuzzle)

<p align="right">(<a href="#top">back to top</a>)</p>