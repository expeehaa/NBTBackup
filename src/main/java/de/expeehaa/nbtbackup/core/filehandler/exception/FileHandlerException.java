package de.expeehaa.nbtbackup.core.filehandler.exception;

public class FileHandlerException extends Exception {
    private final Exception innerException;

    public FileHandlerException(Exception innerException){
        super();
        this.innerException = innerException;
    }

    public Exception getInnerException(){
        return innerException;
    }
}
