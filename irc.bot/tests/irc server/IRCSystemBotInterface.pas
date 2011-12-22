{
JoinMe! is an IRC server based on the RFC 1459 mIRC client & Bahamut server commands/replies
Copyright 2001(02) by Elias Konstadinidis

This program is free software; you can redistribute it and/or modify it under the terms of
the GNU General Public License as published by the Free Software Foundation; either version
2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if
not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA
}

unit IRCSystemBotInterface;

{
  IRCSystemBotInterface unit
  (c) 9-7-2001
}

interface

uses
  Windows;

const
  // Bot Options
  BOTOPT_OPERATOR = 1;
  BOTOPT_VISIBLE  = 2;

type
  // Message structure
  PIRCBotMessage = ^TIRCBotMessage;
  TIRCBotMessage = record
    size             : integer;
    Name             : PChar;
    User             : PChar;
    Host             : PChar;
    Command          : PChar;
    Params           : PChar;
    IsNumericCommand : boolean;
    HasPrefix        : boolean;
    NameIsServer     : boolean;
  end;
  // Bot procedure call
  TBotsndMsgProc = procedure(cMsg : PChar; IRCBotMessage : PIRCBotMessage; SocketID:DWord); stdcall;
  // Bot Init record
  TBotIdentifyData = record
    pBotName   : PChar;
    pBotPass   : PChar;
    BotOptions : cardinal;
    Icon       : HICON;
  end;
  // Dll exported functions
  TFuncBotIdentify     = function(var BotIdentifyData:TBotIdentifyData): boolean; stdcall;
                                                                         {BotIdentify function}
  TFuncBotConnect      = function(SendMessageProc : TBotsndMsgProc;
                                  SocketID:DWord): boolean; stdcall;     {BotConnect function}
  TFuncBotMessage      = procedure(const IRCBotMessage : TIRCBotMessage;
                                   cMsg : PChar); stdcall;               {BotMessage function}
  TFuncBotDisconnect   = procedure; stdcall;                             {BotDisconnect function}
  TFuncBotFinalization = procedure; stdcall;                             {BotFinalization function}
  TFuncBotConfig       = procedure; stdcall;                             {BotConfig function}

implementation

end.
