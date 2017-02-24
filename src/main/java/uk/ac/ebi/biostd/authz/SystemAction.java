package uk.ac.ebi.biostd.authz;

public enum SystemAction
{
 READ("Read object",ActionGroup.OBJECT_ACCESS),
 CHANGE("Amend object",ActionGroup.OBJECT_ACCESS),
 DELETE("Delete object",ActionGroup.OBJECT_ACCESS),
 CREATE("Instantiate object",ActionGroup.INSTANTATION),
 

 CREATESUBM("Create submission", ActionGroup.SUBMISSION ),
 ATTACHSUBM("Attach submission", ActionGroup.SUBMISSION ),
 LISTALLSUBM("List all submissiona", ActionGroup.SUBMISSION ),

 EXPORT_DATA("Export all data", ActionGroup.EXPORT ),
 CONTROLEXPORT("Start/stop export task", ActionGroup.EXPORT ),
 LOCKEXPORT("Lock export task", ActionGroup.EXPORT ),
 
 READOBJTAGS("Read object tags", ActionGroup.OBJECT_ACCESS ),
 CTRLOBJTAGS("Controll object tags", ActionGroup.OBJECT_ACCESS ),
 
 READSUBMTAGS("Read submission tags", ActionGroup.SUBMISSION ),
 CTRLSUBMTAGS("Controll submission tags", ActionGroup.SUBMISSION ),
 
 CREATEIDGEN("Create new ID generator", ActionGroup.IDGEN),
 INCREMENT("Increment ID generator", ActionGroup.IDGEN),
 
 
 MANAGETAGS("Create/rename/delete tags and classifiers",ActionGroup.TAGS),
 
 CREATEGROUP("Create group",ActionGroup.GROUPS);
 
 public static enum ActionGroup
 {
  OBJECT_ACCESS("Common object access"),
  INSTANTATION("Object instantation"),
  SUBMISSION("Submissions"), 
  EXPORT("Data export"), 
  IDGEN("ID generation"),
  TAGS("Tags and classifiers"),
  GROUPS("Group management");

  String description;
  
  ActionGroup( String d )
  {
   description = d;
  }

  public String getDescription()
  {
   return description;
  }

 };
 
 ActionGroup group;
 String description;
 
 SystemAction(String desc, ActionGroup grp)
 {
  group=grp;
  description=desc;
 }

 public ActionGroup getGroup()
 {
  return group;
 }

 public String getDescription()
 {
  return description;
 }
}
