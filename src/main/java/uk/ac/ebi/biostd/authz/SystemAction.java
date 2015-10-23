package uk.ac.ebi.biostd.authz;

public enum SystemAction
{
 READ("Read object",ActionGroup.OBJECT_ACCESS),
 CHANGE("Amend object",ActionGroup.OBJECT_ACCESS),
 DELETE("Delete object",ActionGroup.OBJECT_ACCESS),
 CREATE("Instantiate object",ActionGroup.INSTANTATION),
 

 CREATESUBM("Create submission", ActionGroup.SUBMISSION ),
 ATTACHSUBM("Attach submission", ActionGroup.SUBMISSION ),

 EXPORT_DATA("Export all data", ActionGroup.EXPORT ),
 
 READOBJTAGS("Read object tags", ActionGroup.OBJECT_ACCESS ),
 CTRLOBJTAGS("Controll object tags", ActionGroup.OBJECT_ACCESS ),
 
 READSUBMTAGS("Read submission tags", ActionGroup.SUBMISSION ),
 CTRLSUBMTAGS("Controll submission tags", ActionGroup.SUBMISSION );

 
 public static enum ActionGroup
 {
  OBJECT_ACCESS("Common object access"),
  INSTANTATION("Object instantation"),
  SUBMISSION("Submissions"), 
  EXPORT("Data export");

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
