package com.ibm.sso.rest;

import com.aef3.data.SortUtil;
import com.aef3.data.api.qbe.SortObject;
import com.aef3.data.api.qbe.StringSearchType;
import com.ibm.sso.dto.WebPageDto;
import com.ibm.sso.service.WebPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ibm.sso.service.PagedResult;import java.util.Date;

import org.springframework.security.access.annotation.Secured;
import com.ibm.sso.security.AccessRoles;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;


/* Generated By AEF Generator ( Powered by Dr.Adldoost :D ) */

@RestController
@RequestMapping("/webPage")
public class WebPageRestService {

    private final WebPageService webPageService;

    @Autowired
    public WebPageRestService(WebPageService webPageService) {
        this.webPageService = webPageService;
    }

    @Secured(AccessRoles.ROLE_FIND_WEB_PAGE)
    @GetMapping("/{id}")
    public WebPageDto findById(@PathVariable(name = "id")Long id) {
        return webPageService.findByPrimaryKey(id);
    }

    @Secured(AccessRoles.ROLE_SEARCH_WEB_PAGE)
    @GetMapping("/search")
    public PagedResult search(
                                      @RequestParam(value = "address", required = false) String address,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "id", required = false) Long id,
                                      @RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "content", required = false) String content,
                                      @RequestParam(value = "firstIndex", required = false) Integer firstIndex,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "sortField", required = false) String sortField,
                                      @RequestParam(value = "sortOrder", required = false) String sortOrder) {

            SortObject sortObject = SortUtil.generateSortObject(sortField, sortOrder);
            List<SortObject> sortObjectList = null;
            if(sortObject != null)
               sortObjectList = Collections.singletonList(sortObject);

            if(firstIndex == null)
               firstIndex = 0;
            if(pageSize == null)
               pageSize = Integer.MAX_VALUE;
            WebPageDto webPage = new WebPageDto();
            webPage.setAddress(address); 
            webPage.setName(name); 
            webPage.setId(id); 
            webPage.setTitle(title); 
            webPage.setContent(content); 

            return webPageService.findPagedByExample(webPage,
                   sortObjectList,
                   firstIndex,
                   pageSize,
                   StringSearchType.EXPAND_BOTH_SIDES,
                   null,
                   null
                   );
    }


    @Secured(AccessRoles.ROLE_SAVE_WEB_PAGE)
    @PostMapping(path = "/save")
    public WebPageDto save(@RequestBody WebPageDto webPage) {
        return webPageService.save(webPage);
    }


    @Secured(AccessRoles.ROLE_REMOVE_WEB_PAGE)
    @DeleteMapping(path = "/remove/{id}")
    public void remove(@PathVariable(name = "id")Long id) {
        webPageService.remove(id);
    }
}