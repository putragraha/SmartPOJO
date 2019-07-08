# Annotation Processing Implementation in Android
An experimental Android application which implement annotation processing

## Explanation
### Annotation Processing
- The way of reducing boilerplate in Java code
- Annotation provide metadata on runtime, as for annotation processing may provide on compile time
- Will automatically generate source code on compile time based on annotation specified

## Tech Stack
- Java 8
- Android

## How to Use
- Place annotation @Smart at Class and @Getter or @Setter at its field
- Compile or build project (in Android Studio can be done through **Build -> Rebuild Project**) to auto generate Smart POJO
- A new POJO with prepended "Smart" in its name is now already generated and ready to use
