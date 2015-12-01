//package DataAccess;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import DataAccess.Building;
//import AStar.Node;
//
//public class RoomReader {
//	
//	public static List<Building> getBuildingList(String path)
//	{
//		List<Building> buildingList = new ArrayList<Building>();
//		BufferedReader br = null;
//		String line = "";
//		String delimiter = ",";
//		int buildingLineNum = 0;
//		try {
//			br = new BufferedReader(new FileReader(path));
//			int i = 0;
//			while ((line = br.readLine()) != null) {
//				if (i == 179)
//				{
//					int a = 0;
//					System.out.println(a);
//				}
//				// use comma as separator
//				String[] buildingData = line.split(delimiter);
//				if (i == buildingLineNum)
//				{
//					for(int j = 0; j < buildingData.length; j++)
//					{
//						buildingList.add(new Building(buildingData[j], null));
//					}
//				}
//				else
//				{
//					if(buildingList.size() > 0)
//					{
//						for (int j = 0; j < buildingData.length; j++)
//						{
//							if (buildingData[j] != " " &&  buildingData[j].length() != 0 && buildingData[j] != "" && j < buildingList.size())
//							{
//								if (buildingList.get(j).getRooms() == null)
//								{
//									buildingList.get(j).setRooms(new ArrayList<Room>());
//								}
//								buildingList.get(j).getRooms().add(new Room(buildingData[j]));
//							}
//						}
//					}
//				}
//				i++;
//			}
//
//		} 
//		catch (FileNotFoundException e) {e.printStackTrace();} 
//		catch (IOException e) {e.printStackTrace();} 
//		finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {e.printStackTrace();}
//			}
//		}
//		return buildingList;
//	}
//}
