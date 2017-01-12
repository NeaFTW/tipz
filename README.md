# Tipz #

#Travis public [![Build Status](https://travis-ci.org/NeaFTW/tipz.svg?branch=master)](https://travis-ci.org/NeaFTW/tipz)

#Travis Private [![Build Status](https://travis-ci.com/NeaFTW/tipz.svg?token=rmCpxryC3znpRh9uHNKP&branch=master)](https://travis-ci.com/NeaFTW/tipz)

## How to install the prerequisites ##

```sh
$ sudo chmod +x install.sh
$ ./install.sh
```

Follow the instructions during the installation


## How import the database ##

```sh
$ mongo localhost:27017/tipz ::path::/database/initDb.js
```

## Build & Run with Docker ##

```sh
$ sudo chmod u+x sbt
$ sudo ./sbt
$ > ~;jetty:stop;jetty:start
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost) in your browser.

## Build & Run with Docker ##

```sh
$ docker build -t=tipz .
$ docker run -it -p 8080:8080 tipz
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/tipz) in your browser.
