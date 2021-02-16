package de.expeehaa.nbtbackup.core.filehandler.exception;

public class DiffGenerationException extends FileHandlerException {
    public DiffGenerationException(Exception innerException) {
        super(innerException);
    }
}
