package com.utd.scc.squee.policy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class AdminFunctions {

	private ArrayList<Member> memberForTheRole = null ;
	private ArrayList<Group> role = null;
	private LoadDataXML loadDataXml ;
	private XMLCreator xmlCreator ;
	private String xmlFileName ;
	private String textFileName ;
	
	public AdminFunctions(String xmFile ,String textFile){
		
		this.xmlFileName = xmFile;
		this.textFileName = textFile ;
		loadDataXml =  new LoadDataXML(xmlFileName, textFileName );
                loadDataXml.loadDataFromXML();
		memberForTheRole = new ArrayList<Member>();
		role = new ArrayList<Group>();
	}
	
	public void addRole(String newRole) throws FileNotFoundException, IOException{
		//this.role =  new Group(memberForTheRole, role);
		Group newRoleGroup =  new Group(memberForTheRole, newRole);
		role.add(newRoleGroup);
		getLoadDataXml().setNewGroupsToAdd(role);
		getLoadDataXml().run();
		xmlCreator =  new XMLCreator(getLoadDataXml().groups);
		xmlCreator.run();
	}
	
	public void addUserToRole(String newRole,String user) throws FileNotFoundException, IOException{
		Member newMember =  new Member(user);
		memberForTheRole.add(newMember);
		Group newRoleGroup =  new Group(memberForTheRole, newRole);
		role.add(newRoleGroup);
		getLoadDataXml().setNewGroupsToAdd(role);
		getLoadDataXml().run();
		xmlCreator =  new XMLCreator(getLoadDataXml().groups);
		xmlCreator.run();
		
	}

	public void changePermissions(String role){
		
	}
	
//	public static void main(String[] args){
//		String role =  "manager";
//		String role2 = "Submanager";
//		AdminFunctions admin =  new AdminFunctions("G:\\Spring Classes\\Sanda\\groups.xml", null);
//		try {
////			admin.addRole(role);
////			admin.addRole(role2);
//			admin.addUserToRole("manager", "admin");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

    /**
     * @return the loadDataXml
     */
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
