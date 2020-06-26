package jp.core.repository;

import java.util.HashMap;
import java.util.Map;

import jp.core.exception.SystemException;

public class RepositoryFactory {

    private static Map<String, String> repositories = new HashMap<>();

    private static String dataContext = null;

    public static <T extends DataRepository> void addRepository(String name, Class<T> clazz) {
        repositories.put(name, clazz.getName());
    }

    public static void setDataContext(String contextPath)
    throws SystemException{

        dataContext = contextPath;

        DataRepository.setDataContext(dataContext);
    }

    public static DataRepository getRepository(String entityName)
    throws SystemException{

        DataRepository repository = null;

        String repositoryName = repositories.get(entityName);

        try {
            if (repositoryName == null) throw new Exception("指定されたURIに対するRepositoryがありません");

            repository = 
                (DataRepository)Class.forName(repositoryName)
                .getConstructor().newInstance();

            return repository;

        } 
        catch(Exception e){
            throw new SystemException("ERROR Repository起動不可 ENTITY:" + entityName + " Repository:" + repositoryName, e);
        }     

    }

    private RepositoryFactory(){}    
}