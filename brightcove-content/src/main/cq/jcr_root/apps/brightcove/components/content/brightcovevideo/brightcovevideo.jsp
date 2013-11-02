<%
/*    
    Adobe CQ5 Brightcove Connector  
    
    Copyright (C) 2011 Coresecure Inc.
        
        Authors:    Alessandro Bonfatti
                    Yan Kisen
        
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
%>
<%@include file="/libs/foundation/global.jsp"%>
<%@ page import="com.day.cq.wcm.api.components.DropTarget,java.util.UUID,com.brightcove.proserve.mediaapi.webservices.*"%>
 <%
 
 String containerClass = DropTarget.CSS_CLASS_PREFIX + "brightcovevideo";


String position= properties.get("align","center");
String margLeft = "auto";
String margRight = "auto";
BrcService brcService = BrcUtils.getSlingSettingService();
String playerId = brcService.getDefVideoPlayerID();
String playerKey = brcService.getDefVideoPlayerKey();

if (position.equals("left")) {
    margLeft ="0";
} else if (position.equals("right")) {
    margRight = "0";
}
UUID video_uuid = new UUID(64L,64L);
String VideoRandomID = new String(video_uuid.randomUUID().toString().replaceAll("-",""));
%>
<div style="margin-bottom: 0;margin-left: <%=margLeft%>;margin-right: <%=margRight%>;margin-top: 0;overflow-x: hidden;overflow-y: hidden;text-align: center;width: <%=properties.get("width","480")%>px;text-align:<%=properties.get("align","center")%>;">
    <div id="<%=VideoRandomID %>">

   </div>

    
    <cq:includeClientLib js="brc.BrightcoveExperiences-custom"/>
    <script type="text/javascript">
    
        
    // listener for media change events
        function onMediaBegin(event) {
            var BCLcurrentVideoID;
            var BCLcurrentVideoNAME;
            BCLcurrentVideoID = BCLvideoPlayer.getCurrentVideo().id;
            BCLcurrentVideoNAME = BCLvideoPlayer.getCurrentVideo().displayName;
            switch (event.type) {
                case "mediaBegin":
                    var currentVideoLength ="0";
                    currentVideoLength = BCLvideoPlayer.getCurrentVideo().length;
                    if (currentVideoLength != "0") currentVideoLength = currentVideoLength/1000;
                    if (typeof _gaq != "undefined") _gaq.push(['_trackEvent', location.pathname, event.type+" - "+currentVideoLength, BCLcurrentVideoNAME+" - "+BCLcurrentVideoID]);
                    break;
                case "mediaPlay":
                    _gaq.push(['_trackEvent', location.pathname, event.type+" - "+event.position, BCLcurrentVideoNAME+" - "+BCLcurrentVideoID]);
                    break;
                case "mediaStop":
                    _gaq.push(['_trackEvent', location.pathname, event.type+" - "+event.position, BCLcurrentVideoNAME+" - "+BCLcurrentVideoID]);
                    break;
                case "mediaChange":
                    _gaq.push(['_trackEvent', location.pathname, event.type+" - "+event.position, BCLcurrentVideoNAME+" - "+BCLcurrentVideoID]);
                    break;
                case "mediaComplete":
                    _gaq.push(['_trackEvent', location.pathname, event.type+" - "+event.position, BCLcurrentVideoNAME+" - "+BCLcurrentVideoID]);
                    break;
                default:
                    _gaq.push(['_trackEvent', location.pathname, event.type, BCLcurrentVideoNAME+" - "+BCLcurrentVideoID]);
            }
        }

    </script>
    <script>
        customBC.createVideo("<%=properties.get("width","480")%>","<%=properties.get("height","270")%>","<%=properties.get("playerID",playerId)%>","<%=properties.get("playerKey",playerKey)%>","<%=properties.get("videoPlayer","")%>","<%=VideoRandomID %>");
	</script>
</div>