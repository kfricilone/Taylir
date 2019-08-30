# Taylir
[![Build Status](https://travis-ci.org/kfricilone/Taylir.svg?branch=master)](https://travis-ci.org/kfricilone/Taylir)
[![Coverage Status](https://coveralls.io/repos/github/kfricilone/Taylir/badge.svg?branch=master)](https://coveralls.io/github/kfricilone/Taylir?branch=master)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/kfricilone/Taylir.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/kfricilone/Taylir/context:java)
[![License](https://img.shields.io/badge/License-BSD%202--Clause-orange.svg)](https://opensource.org/licenses/BSD-2-Clause)

Taylir - A soon to be neat intermediate representation tool chain

## Layout

- common-arch - Contains common code for architecture implementations
- common-comp - Contains common code for compiler implementations
- common-llir - Contains common code for a low level intermediate representation
- common-mlir - Contains common code for a medium level intermediate representation
- common-obf - Contains common code for obfuscator implementations
- common-opt - Contains common code for optimizer implementations

- java-arch - Contains code for the Java architecture
- java-comp - Contains code for a Java compiler
- java-obf - Contains code for a Java obfuscator
- java-opt - Contains code for a Java optimizer

## Plans

Current plans for Taylir is to create an Static Single-Assignment (SSA) based intermediate representation (IR) to build a java compiler on top of. The plan is to create the IR in a way that it can be applied to multiple source/target languages.Since the Java Virtual Machine is stack based, the focus of the IR will be to support that. In the future, register based IR support will be added.


## License

Most of Taylir is licensed under the BSD 2-clause license. See the license header in the respective file to be sure.
