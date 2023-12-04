#### Chitter API -  A RESTful Backend Chitter Service using Java Spring and MongoDB

<a name="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/OanaDemian/chitter-java-api">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Chitter RESTful API Service </h3>

  <p align="center">
This is the backend API to Chitter, a social media application, written in Java, using Spring Boot and MongoDB. Chitter was my last solo project for Digital Futures Academy and in the initial attempt I used Node, Express and Mongoose for the server tier. </p>

[//]: # (    <br />)
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    <a href="https://github.com/OanaDemian/chitter-java-api"><strong>Explore the docs »</strong></a>
    <br />
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>

  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

I got the Oracle Java Certified Foundations Associate certification but I hadn't had the opportunity to do any larger scale Java projects. I wanted to learn Spring Boot to see how the Java ecosystem compares to Node. As this was my first time using Spring Boot, my focus was getting to grips with the framework so it's not yet as feature rich as my *[Chitter Node implementation](https://github.com/OanaDemian/chitter-MERN)* which used JWT authentication.

I started by following a tutorial on creating a RESTful Backend Todo Service using Java Spring Boot and MongoDB. This used start.spring.io's _spring initializr_ tool to set up the project, selecting Maven Project to help manage the project dependencies: *Spring Data MongoDB*, *Spring Data Rest* and *Spring Web Services*. Spring Initializr generated many files, amongst which HELP.md file. This provided further information on project dependencies.

Once I completed the tutorial, I spent some time trying to understand Spring Boot Architecture and the data flow in a Spring Boot application. 

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* [![MongoDB][MongoDB]][MongoDB-url]
* [![Spring Boot][Spring Boot]][Spring Boot-url]
* [![Maven][Apache Maven]][Apache Maven-url]
* [![Java][Java]][Java-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

Follow these instructions to set up your project locally.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  ```

### Installation
1. Fork this repository and then:

```sh
$ git clone https://github.com/<YOUR GITHUB USERNAME>/chitter-java-api.git && cd chitter-java-api
```
2. Install Maven dependencies
   ```sh
   ```
3. Connect to the database:
   ```sh
   ```

    <p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ENHANCEMENTS -->
## Future enhancements
- I would like to rewrite bits of this app so it's using the same API as my node implementation and secure the new peep route with authentication so that i can hook it up with the React App I made in my MERN Stack implementation. 

<!-- ROADMAP -->
## Roadmap

- [ ] Get all peeps
- [ ] Create new peeps
- [ ] Sign up
- [ ] Sign in

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- PLANNING ROUTES -->
## End Points 

| Route Name                       | URL Path   | HTTP Method | Purpose                      |
| -------------------------------- | ---------- | ----------- | ---------------------------- |
| Get All Peeps                    | /peeps     | GET         | Display all peeps            |
| New Peep                         | /peeps     | POST        | Adds a new Peep              |
| Sign Up                          | /signup    | POST        | Creates a new User Account   |
| Sign In                          | /login     | POST        | Returns user access details  |

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[MongoDB]:https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white
[MongoDB-url]:https://www.mongodb.com
[Spring Boot]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Spring Boot-url]:https://spring.io/projects/spring-boot
[Apache Maven]:https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[Apache Maven-url]:https://maven.apache.org/
[Java]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]:https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html

