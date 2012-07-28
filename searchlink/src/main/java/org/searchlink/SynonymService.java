package org.searchlink;

import org.searchlink.domain.Sense;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 6/20/12
 * Time: 1:49 PM
 */

public class SynonymService {

    private File file;

    private final Logger logger = Logger.getLogger(SynonymService.class.getName());

    private HashMap<String, String> map = new HashMap<String, String>();

    public SynonymService() {

    }

    @Required
    public void setFile(String fileName) {

        file = new File(fileName);
        logger.info("Wordnet file is in: " + file.getAbsolutePath());
    }

    public String getSynonym(String word) {
          return map.get(word);
    }

    @Transactional
    public void fillSynonyms() throws IOException {
        if (Sense.countSenses() <= 0) {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            do {
                String line = reader.readLine();
                if (line == null) break;
                logger.finer("Parsing: " + line);
                Scanner scanner = new Scanner(line);
                String type = scanner.next();
                String description = scanner.next();
                String name = scanner.next();
                name = name.replace("[", "").replace("]", "");
                logger.finer("type:" + type);
                logger.finer("description:" + description);
                logger.finer("name:" + name);
                scanner = scanner.useDelimiter(",");
                while (scanner.hasNext()) {
                    String synonymName = scanner.next().trim();
                    if (synonymName.length() == 0) continue;
                    logger.finer("synonym:" + synonymName);
                    if (Sense.findSensesByWordEquals(synonymName).getResultList().size() > 0) continue;
                    Sense synonym = new Sense();
                    synonym.setWord(synonymName);
                    synonym.setSense(name);
                    synonym.persist();
                }
            } while (true);

            reader.close();
        }

        List<Sense> senses = Sense.findAllSenses();
        for (Sense sense: senses) {
            map.put(sense.getWord(),sense.getSense());
        }
    }

}
