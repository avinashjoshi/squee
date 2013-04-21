package com.utd.scc.squee.policy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class AdminFunctions {

        private HashMap<String,ArrayList<String>> policyFiles = null;
	private ArrayList<Member> memberForTheRole = null ;
	private ArrayList<Group> role = null;
	private LoadDataXML loadDataXml ;
	private XMLCreator xmlCreator ;
	private String xmlFileName ;
	private String textFileName ;
	private XPDP policyEvaluator = null;
        private PolicyBuilder policyBulider = null;
	
	public AdminFunctions(String xmFile ,String textFile){
		
		this.xmlFileName = xmFile;
		this.textFileName = textFile ;
		policyFiles =  new HashMap<String, ArrayList<String>>();
		loadDataXml =  new LoadDataXML(xmlFileName, textFileName );
		loadDataXml.loadDataFromXML();
		policyBulider = new PolicyBuilder();
		memberForTheRole = new ArrayList<Member>();
		role = new ArrayList<Group>();
		
	}
	
	public void addRole(String newRole) throws FileNotFoundException, IOException{
		//this.role =  new Group(memberForTheRole, role);
		Group newRoleGroup =  new Group(memberForTheRole, newRole);
		role.add(newRoleGroup);
		loadDataXml.setNewGroupsToAdd(role);
		loadDataXml.run();
		xmlCreator =  new XMLCreator(loadDataXml.groups);
		xmlCreator.run();
	}
	
	public void addUserToRole(String newRole,String user) throws FileNotFoundException, IOException{
		Member newMember =  new Member(user);
		memberForTheRole.add(newMember);
		Group newRoleGroup =  new Group(memberForTheRole, newRole);
		role.add(newRoleGroup);
		loadDataXml.setNewGroupsToAdd(role);
		loadDataXml.run();
		xmlCreator =  new XMLCreator(loadDataXml.groups);
		xmlCreator.run();
		
	}

	public void addPolicyFiles(String fileName,ArrayList<String> rolesList){
		policyFiles.put(fileName, rolesList);
		String[] roles = new String[rolesList.size()];
		int i = 0;
		for(String strng : rolesList ){
			roles[i] =strng;
			i++;
		}
		policyBulider.run(fileName, roles);
         }
       
	public boolean checkPermissions(String groupFile ,String userName, ArrayList<String> resourceList){
		String[] policyFilesList = new String[policyFiles.size()];
		boolean[] permit = new boolean[1];
		int i = 0;
		  try {
			  for(String resourceName : policyFiles.keySet()){
				  policyFilesList[i] = "G:\\Spring Classes\\Sanda\\" + resourceName + ".xml";
				  i++;
			  }
			  policyEvaluator =  new XPDP(policyFilesList);
			  for(String resource : resourceList){  
			  policyEvaluator.run(groupFile, resource, policyEvaluator, userName, permit);
				  if(!permit[0]){
					  break ;
				  }
			  	}
			  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return permit[0];
	}
//	public static void main(String[] args){
//		String role =  "manager";
//		String role2 = "Submanager";
//		AdminFunctions admin =  new AdminFunctions("G:\\Spring Classes\\Sanda\\groups.xml", null);
//		ArrayList<String> rolesList =  new ArrayList<String>();
//		ArrayList<String> resourceList = new ArrayList<String>();
//		rolesList.add("manager");
//		rolesList.add("Admin");
//		rolesList.add("guest");
//		try {
//			admin.addRole(role);
//			admin.addRole(role2);
//			admin.addUserToRole("guest", "hello");
//			admin.addPolicyFiles("student", rolesList);
//			rolesList.remove(2);
//			admin.addPolicyFiles("faculty", rolesList);
//			resourceList.add("student");
//			resourceList.add("faculty");
//			boolean result = admin.checkPermissions("G:\\Spring Classes\\Sanda\\groups.xml", "song", resourceList );
//			System.out.println(result);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
 public LoadDataXML getLoadDataXml() {
        return loadDataXml;
    }

    /**
     * @param loadDataXml the loadDataXml to set
     */
    public void setLoadDataXml(LoadDataXML loadDataXml) {
        this.loadDataXml = loadDataXml;
    }	   
	
}