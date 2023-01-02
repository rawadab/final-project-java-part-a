package Proyect;

public class ClientNotExsit extends Exception {
    public ClientNotExsit() {
        super("The Clint you are trying to enter is not exist");
    }
}/*class that extends from exception and print the message*/
