# Test example

## Description

This is example project for [Medium article](https://artemzip.medium.com/a01a004751db?source=friends_link&sk=f62bbc2164bdfdaf4102107e2ccc7d1e), that shows how to run same set of tests towards multiple environments

## How to run

### Build
```shell
gradle build
```

### Run integration tests with local env
```shell
gradle integration-tests:test
```

### Run integration tests on remote host
```shell
gradle integration-tests:remoteTest -PremoteUrl=http://localhost:8080
```

### Run smoke integration tests on remote host
```shell
gradle integration-tests:smokeTest -PremoteUrl=http://localhost:8080
```
