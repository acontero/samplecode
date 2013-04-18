//To initialize Network Communication with server
- (BOOL)initNetworkCommunication {
    //Obtain IP address from HomeComputer.txt file saved on iPhone
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:@"ConnectionData//HomeComputer.txt"];
    NSLog(@"filePath in initNetworkCommunication method is %@", filePath);
    if ([[NSFileManager defaultManager] fileExistsAtPath:filePath] == NO)
    {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error: Missing Necessary Information" message:@"Please re-enter an IP address and port number in order to proceed." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        return false;
    }
    NSString *content = [NSString stringWithContentsOfFile:filePath encoding: NSUTF8StringEncoding error:nil];
    NSArray *connectionInformation = [content componentsSeparatedByString:@","];
    NSString *ipAddress = [connectionInformation objectAtIndex:0];
    NSString *portString = [connectionInformation objectAtIndex:1];
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setNumberStyle:NSNumberFormatterNoStyle];
    NSNumber * myNumber = [f numberFromString:portString];
    unsigned long portNumber = [myNumber unsignedLongValue];
    readStream = NULL;
    CFStreamCreatePairWithSocketToHost(NULL, (__bridge CFStringRef)ipAddress,portNumber, &readStream, &writeStream); 
        
    inputStream = objc_unretainedObject(readStream);
    outputStream = objc_unretainedObject(writeStream);    
    [inputStream setDelegate:self];
    [outputStream setDelegate:self];
    [inputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
    [outputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
    [inputStream open];
    [outputStream open];
    
    
    //Check connection.  Alert user if connection error occurs and pop back to previous view, otherwise, load directories
    NSUInteger connectionResult = [inputStream streamStatus];
    while((connectionResult!=NSStreamStatusOpen) && (connectionResult!=NSStreamStatusError))
    {
        connectionResult = [inputStream streamStatus];
        NSLog(@"Connection result is: %d",connectionResult);
        if(connectionResult==NSStreamStatusError)
        {
            UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Connection Failed" message:@"Cannot establish connection with the host at this time." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            return false;
        }
        if(connectionResult==NSStreamStatusOpen)
        {
            return true;
        }
    }
    return false;
}