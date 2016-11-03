//
//  reactnativeomnitureapi.m
//  reactnativeomnitureapi
//
//  Created by Vivek Parekh on 11/2/16.
//  Copyright © 2016 Vivek Parekh. All rights reserved.
//

#import "reactnativeomnitureapi.h"
#import "ADBMobile.h"

@implementation reactnativeomnitureapi

RCT_EXPORT_MODULE();


RCT_EXPORT_METHOD(trackAction:(NSString *)page contextData:(NSDictionary *)contextData)
{
    NSLog(@"Calling Track Action %@", contextData);
    ADBMobile.debugLogging = true;
    [ADBMobile trackAction:page data:contextData];
}

RCT_EXPORT_METHOD(trackState:(NSString *)page contextData:(NSDictionary *)contextData)
{
    NSLog(@"Calling Track State %@", page);
    [ADBMobile trackState:page data:contextData];

}

@end
