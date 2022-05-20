@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  TripMaster startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and TRIP_MASTER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\TripMaster-plain.jar;%APP_HOME%\lib\spring-boot-starter-web-2.6.7.jar;%APP_HOME%\lib\spring-boot-starter-actuator-2.6.7.jar;%APP_HOME%\lib\spring-boot-starter-test-2.6.7.jar;%APP_HOME%\lib\mockito-junit-jupiter-4.0.0.jar;%APP_HOME%\lib\junit-jupiter-params-5.8.2.jar;%APP_HOME%\lib\junit-jupiter-engine-5.8.2.jar;%APP_HOME%\lib\junit-jupiter-api-5.8.2.jar;%APP_HOME%\lib\junit-platform-engine-1.8.2.jar;%APP_HOME%\lib\junit-platform-commons-1.8.2.jar;%APP_HOME%\lib\junit-jupiter-5.8.2.jar;%APP_HOME%\lib\jsoniter-0.9.23.jar;%APP_HOME%\lib\annotations-23.0.0.jar;%APP_HOME%\lib\spring-cloud-starter-openfeign-3.1.2.jar;%APP_HOME%\lib\spring-boot-starter-json-2.6.6.jar;%APP_HOME%\lib\spring-cloud-starter-3.1.2.jar;%APP_HOME%\lib\spring-cloud-openfeign-core-3.1.2.jar;%APP_HOME%\lib\spring-boot-starter-aop-2.6.6.jar;%APP_HOME%\lib\spring-boot-starter-2.6.6.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-2.6.6.jar;%APP_HOME%\lib\spring-webmvc-5.3.18.jar;%APP_HOME%\lib\feign-form-spring-3.8.0.jar;%APP_HOME%\lib\spring-web-5.3.18.jar;%APP_HOME%\lib\spring-boot-actuator-autoconfigure-2.6.6.jar;%APP_HOME%\lib\micrometer-core-1.8.4.jar;%APP_HOME%\lib\spring-boot-test-autoconfigure-2.6.6.jar;%APP_HOME%\lib\spring-boot-test-2.6.6.jar;%APP_HOME%\lib\json-path-2.6.0.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.3.jar;%APP_HOME%\lib\assertj-core-3.21.0.jar;%APP_HOME%\lib\hamcrest-2.2.jar;%APP_HOME%\lib\mockito-core-4.0.0.jar;%APP_HOME%\lib\jsonassert-1.5.0.jar;%APP_HOME%\lib\spring-test-5.3.18.jar;%APP_HOME%\lib\spring-boot-autoconfigure-2.6.6.jar;%APP_HOME%\lib\spring-boot-actuator-2.6.6.jar;%APP_HOME%\lib\spring-boot-2.6.6.jar;%APP_HOME%\lib\spring-context-5.3.18.jar;%APP_HOME%\lib\spring-aop-5.3.18.jar;%APP_HOME%\lib\spring-beans-5.3.18.jar;%APP_HOME%\lib\spring-expression-5.3.18.jar;%APP_HOME%\lib\spring-core-5.3.18.jar;%APP_HOME%\lib\xmlunit-core-2.8.4.jar;%APP_HOME%\lib\moneta-convert-imf-1.4.2.jar;%APP_HOME%\lib\moneta-convert-ecb-1.4.2.jar;%APP_HOME%\lib\moneta-convert-1.4.2.jar;%APP_HOME%\lib\moneta-core-1.4.2.jar;%APP_HOME%\lib\money-api-1.1.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\spring-cloud-commons-3.1.2.jar;%APP_HOME%\lib\feign-slf4j-11.8.jar;%APP_HOME%\lib\feign-core-11.8.jar;%APP_HOME%\lib\spring-boot-starter-logging-2.6.6.jar;%APP_HOME%\lib\jakarta.annotation-api-1.3.5.jar;%APP_HOME%\lib\snakeyaml-1.29.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.13.2.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.13.2.jar;%APP_HOME%\lib\jackson-annotations-2.13.2.jar;%APP_HOME%\lib\jackson-core-2.13.2.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.13.2.jar;%APP_HOME%\lib\jackson-databind-2.13.2.2.jar;%APP_HOME%\lib\tomcat-embed-websocket-9.0.60.jar;%APP_HOME%\lib\tomcat-embed-core-9.0.60.jar;%APP_HOME%\lib\tomcat-embed-el-9.0.60.jar;%APP_HOME%\lib\HdrHistogram-2.1.12.jar;%APP_HOME%\lib\LatencyUtils-2.0.3.jar;%APP_HOME%\lib\json-smart-2.4.8.jar;%APP_HOME%\lib\logback-classic-1.2.11.jar;%APP_HOME%\lib\log4j-to-slf4j-2.17.2.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.36.jar;%APP_HOME%\lib\feign-form-3.8.0.jar;%APP_HOME%\lib\slf4j-api-1.7.36.jar;%APP_HOME%\lib\jakarta.activation-api-1.2.2.jar;%APP_HOME%\lib\byte-buddy-1.11.22.jar;%APP_HOME%\lib\byte-buddy-agent-1.11.22.jar;%APP_HOME%\lib\objenesis-3.2.jar;%APP_HOME%\lib\android-json-0.0.20131108.vaadin1.jar;%APP_HOME%\lib\spring-jcl-5.3.18.jar;%APP_HOME%\lib\opentest4j-1.2.0.jar;%APP_HOME%\lib\spring-cloud-context-3.1.2.jar;%APP_HOME%\lib\spring-security-rsa-1.0.10.RELEASE.jar;%APP_HOME%\lib\spring-security-crypto-5.6.2.jar;%APP_HOME%\lib\accessors-smart-2.4.8.jar;%APP_HOME%\lib\bcpkix-jdk15on-1.68.jar;%APP_HOME%\lib\aspectjweaver-1.9.7.jar;%APP_HOME%\lib\commons-fileupload-1.4.jar;%APP_HOME%\lib\logback-core-1.2.11.jar;%APP_HOME%\lib\log4j-api-2.17.2.jar;%APP_HOME%\lib\asm-9.1.jar;%APP_HOME%\lib\bcprov-jdk15on-1.68.jar;%APP_HOME%\lib\commons-io-2.2.jar


@rem Execute TripMaster
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %TRIP_MASTER_OPTS%  -classpath "%CLASSPATH%" TripMaster %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable TRIP_MASTER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%TRIP_MASTER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
