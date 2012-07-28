// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.service;

import java.util.List;
import org.searchlink.domain.Link;
import org.searchlink.service.LinkService;

privileged aspect LinkService_Roo_Service {
    
    public abstract long LinkService.countAllLinks();    
    public abstract void LinkService.deleteLink(Link link);    
    public abstract Link LinkService.findLink(Long id);    
    public abstract List<Link> LinkService.findAllLinks();    
    public abstract List<Link> LinkService.findLinkEntries(int firstResult, int maxResults);    
    public abstract void LinkService.saveLink(Link link);    
    public abstract Link LinkService.updateLink(Link link);    
}