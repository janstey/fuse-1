##### this war will deploy an Angular based web app + a couple of companion servlets

    http://localhost:8181/rhaccess-web/support.html#/case/new  
                                                   #/case/list
                                                   #/case/search
                                                   #/search
                                                   
## test usage
    curl http://localhost:8181/rhaccess-web/support/version
    curl http://localhost:8181/rhaccess-web/support/collect
    curl http://localhost:8181/rhaccess-web/support/version
    curl http://localhost:8181/rhaccess-web/support/collect
    curl http://localhost:8181/rhaccess-web/support/td
    curl http://localhost:8181/rhaccess-web/support/config
    
##### to have hawtio integration you have to install this plugin:
    install -s mvn:io.hawt/hawtio-plugin-redhat-access/1.5-SNAPSHOT/war
    
It will create a tab in Hawtio and give you links to access the web app functionalities.

