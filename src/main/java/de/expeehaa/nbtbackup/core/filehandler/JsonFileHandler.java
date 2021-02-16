package de.expeehaa.nbtbackup.core.filehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import de.expeehaa.nbtbackup.core.filehandler.exception.DiffGenerationException;
import de.expeehaa.nbtbackup.core.filehandler.exception.FileHandlerException;

import java.io.IOException;
import java.nio.file.Path;

public class JsonFileHandler extends AbstractFileHandler {
    @Override
    public void handleFile(Path original, Path compare, Path destination) throws FileHandlerException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if(!haveSameChecksums(original.toFile(), compare.toFile())){
                JsonNode nodeOld = mapper.readTree(readFileContent(compare));
                JsonNode nodeNew = mapper.readTree(readFileContent(original));

                JsonNode patch = JsonDiff.asJson(nodeOld, nodeNew);
            }
        } catch (JsonProcessingException e) {
            throw new DiffGenerationException(e);
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }
}
