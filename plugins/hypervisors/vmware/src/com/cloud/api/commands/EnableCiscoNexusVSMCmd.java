// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package com.cloud.api.commands;

import org.apache.cloudstack.api.*;
import org.apache.log4j.Logger;

import org.apache.cloudstack.api.APICommand;
import com.cloud.event.EventTypes;
import com.cloud.exception.ConcurrentOperationException;
import com.cloud.exception.InsufficientCapacityException;
import com.cloud.exception.ResourceAllocationException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.network.element.CiscoNexusVSMElementService;
import com.cloud.user.Account;
import com.cloud.api.response.CiscoNexusVSMResponse;
import com.cloud.network.CiscoNexusVSMDevice;

@APICommand(name = "enableCiscoNexusVSM", responseObject=CiscoNexusVSMResponse.class, description="Enable a Cisco Nexus VSM device")
public class EnableCiscoNexusVSMCmd extends BaseAsyncCmd {

    public static final Logger s_logger = Logger.getLogger(EnableCiscoNexusVSMCmd.class.getName());
    private static final String s_name = "enablecisconexusvsmresponse";
    @PlugService CiscoNexusVSMElementService _ciscoNexusVSMService;

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @IdentityMapper(entityTableName="virtual_supervisor_module")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, required=true, description="Id of the Cisco Nexus 1000v VSM device to be enabled")
    private Long id;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getCiscoNexusVSMDeviceId() {
        return id;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException {
    	CiscoNexusVSMDevice result = _ciscoNexusVSMService.enableCiscoNexusVSM(this);
        if (result != null) {
        	CiscoNexusVSMResponse response = _ciscoNexusVSMService.createCiscoNexusVSMDetailedResponse(result);
        	response.setResponseName(getCommandName());
        	this.setResponseObject(response);
        } else {
        	throw new ServerApiException(BaseAsyncCmd.INTERNAL_ERROR, "Failed to enable Cisco Nexus VSM device");
        }
    }

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }

    @Override
    public String getEventDescription() {
    	return "Enabling a Cisco Nexus VSM device";
    }

    @Override
    public String getEventType() {
    	return EventTypes.EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_ENABLE;
    }
}
