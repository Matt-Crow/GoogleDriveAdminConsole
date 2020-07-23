package drive.commands.utils;

import drive.commands.implementations.GiveViewAccess;
import drive.commands.implementations.ShareFiles;
import drive.commands.implementations.ReadUserList;
import drive.commands.implementations.ReadFileList;
import drive.commands.implementations.UpdateDownloadAccess;
import java.util.List;
import start.GoogleDriveService;
import structs.GoogleSheetProperties;
import structs.UserToFileMapping;

/**
 * Likely get rid of this in the future
 * @author Matt
 */
public class CommandFactory {
    private final GoogleDriveService services;
    
    public CommandFactory(GoogleDriveService service){
        services = service;
    }
    
    public final ReadUserList readUserList(GoogleSheetProperties info){
        return new ReadUserList(services, info);
    }
    public final ReadFileList readFileList(GoogleSheetProperties info){
        return new ReadFileList(services, info);
    }
    
    public final ShareFiles shareFiles(GoogleSheetProperties formInfo, GoogleSheetProperties fileInfo, boolean isTest){
        return new ShareFiles(services, formInfo, fileInfo, isTest);
    }
    
    public final GiveViewAccess giveAccess(List<UserToFileMapping> mappings){
        return new GiveViewAccess(services, mappings);
    }
    
    public final UpdateDownloadAccess updateDownloadOptions(GoogleSheetProperties fileList){
        return new UpdateDownloadAccess(services, fileList);
    }
}
