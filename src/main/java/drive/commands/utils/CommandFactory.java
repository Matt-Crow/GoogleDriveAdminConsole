package drive.commands.utils;

import drive.commands.implementations.AddToAccessList;
import drive.commands.implementations.CreateAccessList;
import drive.commands.implementations.GetAccessList;
import drive.commands.implementations.GiveViewAccess;
import drive.commands.implementations.ParseCertificationForm;
import drive.commands.implementations.ReadUserList;
import drive.commands.implementations.ReadFileList;
import drive.commands.implementations.SetAccessListContent;
import drive.commands.implementations.UpdateDownloadAccess;
import java.util.List;
import start.ServiceAccess;
import structs.UserListProperties;
import structs.FileListProperties;
import structs.UserToFileMapping;

/**
 *
 * @author Matt
 */
public class CommandFactory {
    private final ServiceAccess services;
    
    public CommandFactory(ServiceAccess service){
        services = service;
    }
    
    /*
    Access list related methods
    */
    
    public final CreateAccessList createAccessListCmd(String folderId){
        return new CreateAccessList(services, folderId);
    }
    public final GetAccessList getAccessListCmd(String accessListId){
        return new GetAccessList(services, accessListId);
    }
    public final SetAccessListContent setAccessListCmd(String accessListId, String[] userNames){
        return new SetAccessListContent(services, accessListId, userNames);
    }
    
    public final AddToAccessList addToAccessListCmd(String accessListId, String[] newUsers){
        return new AddToAccessList(services, accessListId, newUsers);
    }
    
    public final ReadUserList readCertForm(UserListProperties info){
        return new ReadUserList(services, info);
    }
    public final ReadFileList readFileList(FileListProperties info){
        return new ReadFileList(services, info);
    }
    
    public final ParseCertificationForm parseCertificationForm(UserListProperties formInfo, FileListProperties fileInfo, String accessListId, boolean isTest){
        return new ParseCertificationForm(services, formInfo, fileInfo, accessListId, isTest);
    }
    
    public final GiveViewAccess giveAccess(List<UserToFileMapping> mappings){
        return new GiveViewAccess(services, mappings);
    }
    
    public final UpdateDownloadAccess updateDownloadOptions(FileListProperties fileList){
        return new UpdateDownloadAccess(services, fileList);
    }
}
