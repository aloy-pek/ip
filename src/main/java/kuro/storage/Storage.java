package kuro.storage;

public class Storage {
    private String filepath;
    
    public Storage(String filepath) {
        this.filepath = filepath;
    }
//    
//    try {
//            File taskDb = new File("./data/kuro.txt");
//            //Scan kuro.txt and initialize taskList
//            Scanner dbScanner = new Scanner(taskDb);
//            while (dbScanner.hasNextLine()) {
//                String data = dbScanner.nextLine();
//                //todo 
//            }
//            dbScanner.close();
//        } catch (FileNotFoundException e) {
//            File newDb = new File("./data/kuro.txt");
//            try {
//                newDb.createNewFile();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
}
