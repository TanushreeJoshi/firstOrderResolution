package homework3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
import java.util.Map.Entry;

public class homework3 {
	static HashMap<String,ArrayList<Float>>  h= new HashMap<String,ArrayList<Float>>();
	static ArrayList<String> ans=new ArrayList<String>();
	static ArrayList<String> kb;
	static Stack<ArrayList<String>> stack= new Stack<ArrayList<String>>();
	static HashMap<String,String> unifyargs=new HashMap<String,String>();
	static int resolved=0;
	static ArrayList<String> tempkb;
	static HashMap<String,ArrayList<Float>>  temph= new HashMap<String,ArrayList<Float>>();
	static ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
	static String negate(String query)
	{
		if(query.contains("~"))
			return query.substring(1);
		else
			return "~"+query;
	}
	
	static void resolve(ArrayList<String> current,ArrayList<String> possible,int pre,String mp)
	{
		//System.out.println("resolve called");
		String pred; ArrayList<String> fin= new ArrayList<String>(possible);
		//System.out.println(fin);
		System.out.println("current 1 "+current);
		System.out.println("current 2 "+fin);
		String actualPred; 
		for(int i=0;i<current.size();i++)
		{
			//System.out.println("enter");	
			String t1=current.get(i);
			int e=t1.indexOf('(');
			actualPred=t1.substring(0, e);
			if(t1.charAt(0)=='~')pred=actualPred.substring(1);
			else pred='~'+actualPred;
		System.out.println("pred "+pred);
		//System.out.println(actualPred);
			String match="";  int con=0;
			
			if(pred.equals(mp)) {
				System.out.println("Given p match: "+mp);
			
				con=1;
			}
			else{
				for(int k=0;k<fin.size();k++)
			{
				String s=fin.get(k);
				System.out.println("s :"+s);
				if(s.startsWith(pred)) { con=1;
				break;
				}
				else if(s.startsWith(actualPred))
				{
					con=2;
					
				}
				
			}
			}
			
			if(con==1)
			{ 
				if(pred.equals(mp))
				{
					if(pre<fin.size())
					{
						match=fin.get(pre);
					}
					else for(String x:fin) {
						if(x.startsWith(pred))
						{	match=x;
						break;}
					}
					System.out.println("pos: "+pre);
				System.out.println("given pre");
				}
				
				else
				{for(String x:fin) {
					if(x.startsWith(pred))
					{	match=x;
					break;}
				}
				System.out.println("other pre");
				}
				System.out.println("match: "+match);
				String arguments1="";
				for(int j=0;j<t1.length();j++)
				{
					if(t1.charAt(j)=='(')
					{
						j++;
						while(t1.charAt(j)!=')')
						{
							arguments1+=t1.charAt(j);
							j++;
						}
						break;
					}
				}
				String args1[]=arguments1.split(",");
				String arguments2="";
				for(int j=0;j<match.length();j++)
				{
					if(match.charAt(j)=='(')
					{
						j++;
						while(match.charAt(j)!=')')
						{
							arguments2+=match.charAt(j);
							j++;
						}
						break;
					}
				}
				String args2[]=arguments2.split(",");
				/*System.out.print("args2 :");
				for(int i1=0;i1<args2.length;i1++)
					System.out.print(args2[i1]+" ");*/
			//	unifyargs.clear();
				boolean u=unify(args1,args2);
				//System.out.println(u);
				if(u)
				{
					
			for(int a=0;a<fin.size();a++)  //changing args in org sentence
					{
						String curr=fin.get(a);
						Iterator it = unifyargs.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry pair = (Map.Entry)it.next();
							//System.out.println(pair.getKey()+" "+pair.getValue());
							if(curr.contains("("+(String)pair.getKey()+")"))
							{
						curr=curr.replace("("+(String)pair.getKey()+")","("+(String)pair.getValue()+")");
							fin.remove(a);
							fin.add(a, curr);
						//	System.out.println(curr);
							}
							if(curr.contains("("+(String)pair.getKey()+","))
							{
						curr=curr.replace("("+(String)pair.getKey()+",","("+(String)pair.getValue()+",");
							fin.remove(a);
							fin.add(a, curr);
						//	System.out.println(curr);
							}
						if(curr.contains(","+(String)pair.getKey()+")"))
						{
							curr=curr.replace(","+(String)pair.getKey()+")",","+(String)pair.getValue()+")");
							fin.remove(a);
							fin.add(a, curr);
						}
						if(curr.contains(","+(String)pair.getKey()+","))
						{
							curr=curr.replace(","+(String)pair.getKey()+",",","+(String)pair.getValue()+",");
							fin.remove(a);
							fin.add(a, curr);
						}

						}

					}
/*			for(String s:fin)
			{
				System.out.println(s);
			}*/
			
					//String checking="";
					for(int f=0;f<fin.size();f++)
					{
						if(fin.get(f).startsWith(pred))
							fin.remove(f);
					}
				
                    
					System.out.println(fin);
		       }
				else
				{
					return;
				//ans.add("FALSE"); ///check!!!
				//resolved=1;
				}
			}
			else if(con==2)
			{
				for(String x:fin) {
					if(x.startsWith(actualPred))
						match=x;
				}
				
				String arguments1="";
				for(int j=0;j<t1.length();j++)
				{
					if(t1.charAt(j)=='(')
					{
						j++;
						while(t1.charAt(j)!=')')
						{
							arguments1+=t1.charAt(j);
							j++;
						}
						break;
					}
				}
				String args1[]=arguments1.split(",");
				String arguments2="";
				for(int j=0;j<match.length();j++)
				{
					if(match.charAt(j)=='(')
					{
						j++;
						while(match.charAt(j)!=')')
						{
							arguments2+=match.charAt(j);
							j++;
						}
						break;
					}
				}
				String args2[]=arguments2.split(",");
				//unifyargs.clear();
				boolean u=unify(args1,args2);
			//	System.out.println(u);
				if(u)
				{
					
			for(int a=0;a<fin.size();a++)  //changing args in org sentence
					{
						String curr=fin.get(a);
						Iterator it = unifyargs.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry pair = (Map.Entry)it.next();
							//System.out.println(pair.getKey()+" "+pair.getValue());
							if(curr.contains("("+(String)pair.getKey()+")"))
							{
						curr=curr.replace("("+(String)pair.getKey()+")","("+(String)pair.getValue()+")");
							fin.remove(a);
							fin.add(a, curr);
						//	System.out.println(curr);
							}
							if(curr.contains("("+(String)pair.getKey()+","))
							{
						curr=curr.replace("("+(String)pair.getKey()+",","("+(String)pair.getValue()+",");
							fin.remove(a);
							fin.add(a, curr);
						//	System.out.println(curr);
							}
						if(curr.contains(","+(String)pair.getKey()+")"))
						{
							curr=curr.replace(","+(String)pair.getKey()+")",","+(String)pair.getValue()+")");
							fin.remove(a);
							fin.add(a, curr);
						}
						if(curr.contains(","+(String)pair.getKey()+","))
						{
							curr=curr.replace(","+(String)pair.getKey()+",",","+(String)pair.getValue()+",");
							fin.remove(a);
							fin.add(a, curr);
						}
						}

					}
			
					//String checking="";
				/*	for(int f=0;f<fin.size();f++)
					{
						if(fin.get(f).contains(pred))
							fin.remove(f);
					}*/
				
                    
				
		       }
				else
				{
					
					return;
			//ans.add("FALSE"); ///check!!!
				//	resolved=1;
					
				}
			}
			else {
				System.out.println("current added "+current.get(i));
				fin.add(current.get(i));
				
			}
		
		}
		
		 if(fin.isEmpty()) {
        	 ans.add("TRUE");
        	 
        resolved=1;
        return;
         }
		/* for(String s:fin)
			{
				System.out.println(s);
			}*/
		for(ArrayList<String> s: res)
		{
			if(s.equals(fin))
			{resolved=0;	return;}
		}
		
		
		for(int a=0;a<fin.size();a++)  //changing args in org sentence
		{
			String curr=fin.get(a);
			Iterator it = unifyargs.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				System.out.println(pair.getKey()+" "+pair.getValue());
				if(curr.contains("("+(String)pair.getKey()+")"))
				{
			curr=curr.replace("("+(String)pair.getKey()+")","("+(String)pair.getValue()+")");
				fin.remove(a);
				fin.add(a, curr);
			//	System.out.println(curr);
				}
			
				if(curr.contains("("+(String)pair.getKey()+","))
					{
				curr=curr.replace("("+(String)pair.getKey()+",","("+(String)pair.getValue()+",");
					fin.remove(a);
					fin.add(a, curr);
				//	System.out.println(curr);
					}
				if(curr.contains(","+(String)pair.getKey()+")"))
				{
					curr=curr.replace(","+(String)pair.getKey()+")",","+(String)pair.getValue()+")");
					fin.remove(a);
					fin.add(a, curr);
				}
				if(curr.contains(","+(String)pair.getKey()+","))
				{
					curr=curr.replace(","+(String)pair.getKey()+",",","+(String)pair.getValue()+",");
					fin.remove(a);
					fin.add(a, curr);
				}
				//// !!!! make sure only args get updated

			}
		}
		/*for(String s:fin)
		{
			System.out.println(s);
		}*/
			
stack.push(fin);
res.add(fin);
System.out.println(fin);
			 return;

		
		
		
	}
	
	static void checkQuery(String s)
	{ 
  resolved=0;
		s= negate(s);
	
		ArrayList<String> first = new ArrayList<String>();
		first.add(s);
       res.add(first);
		//System.out.println(res.size());
    //   System.out.println(first);
		stack.push(first);
		 //System.out.println(stack.size());
		String pred;


		while(!stack.isEmpty())
		{
			ArrayList<String> currentS = stack.pop();
		System.out.println("From stack: "+currentS);
				for(int k=0;k<currentS.size();k++)	     ///// for each literal of sent to be resolved
				{
					
					String t1=currentS.get(k);
					System.out.println("t1: "+t1);
					int e=t1.indexOf('(');
					pred=t1.substring(0, e);
					if(t1.charAt(0)=='~')pred=pred.substring(1);
					else pred='~'+pred;
					//System.out.println(pred);
					if(temph.containsKey(pred))
					{
						ArrayList<Float> sent= temph.get(pred); int se=0,p=0;
						System.out.println(sent);
						for(int i=0;i<sent.size();i++)          ////resolving with all possible sentences
						{
							
							System.out.println(sent.get(i));
						se= (sent.get(i).intValue()*10)/10;
						System.out.println(se);
					     p=  (int) ((sent.get(i).floatValue()*10)%10);
						//System.out.println(sent.get(i));
			
							String[] t=tempkb.get(se-1).split(" \\| ");
						/*	for(int b=0;b<t.length;b++)
								System.out.print(t[b]+" ");
									System.out.println();*/
							ArrayList<String> t2= new ArrayList(Arrays.asList(t));
							//System.out.println("yo");
							unifyargs.clear();
						resolve(currentS,t2,p-1,pred);
							
							System.out.println("abb");
							if(resolved==1)return;
								
							
			
		                }
					}
					else {
					//	ans.add("FALSE");
						//return;
					}
				}
				
		}
		ans.add("FALSE");
		return;
	}


	static boolean unify(String[] x,String[] y)
	{
		/*System.out.print("X :");
	for(int i1=0;i1<x.length;i1++)
		System.out.print(x[i1]+" ");
	System.out.print("Y :");
	for(int i1=0;i1<x.length;i1++)
		System.out.print(y[i1]+" ");
	System.out.println();*/
		int counter=0;
		if(x.length!=y.length)
			return false;
		for(int i=0;i<x.length;i++)
		{
			if(x[i].charAt(0)>='A'&& x[i].charAt(0)<='Z' && y[i].charAt(0)>='A' && y[i].charAt(0)<='Z')
			{ 
				if(x[i].equals(y[i])) counter++;
				else break;
			} ///constants equal
			else if(x[i].charAt(0)>='A'&& x[i].charAt(0)<='Z' && y[i].charAt(0)>='a' && y[i].charAt(0)<='z')
			{

				if(unifyargs.containsKey(y[i]))
				{
					if(unifyargs.get(y[i]).charAt(0)>='A' && unifyargs.get(y[i]).charAt(0)<='Z'
							&& !unifyargs.get(y[i]).equals(x[i]))
						break;
					else {unifyargs.replace(y[i], x[i]); counter++;}
				}
				else{unifyargs.put(y[i], x[i]); counter++;}
			}	
			else
				if(x[i].charAt(0)>='a'&& x[i].charAt(0)<='z' && y[i].charAt(0)>='a' && y[i].charAt(0)<='z')
				{
					if(unifyargs.containsKey(x[i]))
					{
						unifyargs.put(y[i], unifyargs.get(x[i]));
						counter++;
					}
					else {  
						if(x[i].equals(y[i])) counter++;
						else {unifyargs.put(y[i], x[i]);  counter++;}

					}
				}
				else 
					if(x[i].charAt(0)>='a'&& x[i].charAt(0)<='z' && y[i].charAt(0)>='A' && y[i].charAt(0)<='Z')
					{
						if(unifyargs.containsKey(x[i]))
						{
							if(unifyargs.get(x[i]).charAt(0)>='A' && unifyargs.get(x[i]).charAt(0)<='Z'
									&& !unifyargs.get(x[i]).equals(y[i]))
								break;
							else {unifyargs.replace(x[i], y[i]); counter++;}
						}
						else{unifyargs.put(x[i], y[i]); counter++;}
						/*unifyargs.put(x[i], y[i]);
						counter++;*/
					}
				else
				{
					if(unifyargs.containsValue(y[i]))
					{

						for( Map.Entry<String, String> entry :  unifyargs.entrySet())

						{                   
							if (y[i].equals(entry.getValue())) 
							{

								unifyargs.replace(entry.getKey(),x[i]);

							}
						}
					}

				}
		}
		Iterator it = unifyargs.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey()+" "+pair.getValue());}
		System.out.println(counter);
		if(counter==x.length)
		{
			return true;}
		else
		{
			//System.out.println("false");
			return false;}

	}



	public static void main(String[]args)
	{
		FileInputStream in =null;
		FileOutputStream out=null;
		int q=0,k=0;  
		try {
			in= new FileInputStream("input.txt");
			out = new FileOutputStream("output.txt");
			@SuppressWarnings("resource")
			BufferedReader colr = new BufferedReader(new InputStreamReader(in));
			@SuppressWarnings("resource")
			PrintStream pw = new PrintStream(out);
			q=Integer.parseInt(colr.readLine());
			String[] query =new String[q];
			int i=0;
			while(i<q){
				query[i] = colr.readLine();
				i++;
			}
			k=Integer.parseInt(colr.readLine());

			kb= new ArrayList<String>();
			i=0; String cr; StringBuffer crb; int j1=0;
			while(i<k){
				j1=0;
				cr  = colr.readLine();
			crb = new StringBuffer(cr);
			while(j1<crb.length())
				{
					if(crb.charAt(j1)=='('||crb.charAt(j1)==',')
						if(crb.charAt(j1+1)>='a'&&crb.charAt(j1+1)<='z')
						{   j1++;
							while(crb.charAt(j1)==','||crb.charAt(j1)==')')
								j1++;
							
							crb.insert(j1+1, i+1);
						}
					
					
					j1++;		
				}
				String f=new String(crb);
				kb.add(i, f);
				i++;
			}
			//System.out.println(kb.get(0));
			//int t=0;int c=-1;
			int start=0,end=0;String temp; float pred=0.1f;
			//creating hashmap
			List l;
			for(int a=0;a<k;a++)
			{ start=0;end=0;pred=0.1f;
			String c=kb.get(a);
			while(start<kb.get(a).length())
			{
				//if(kb[a].charAt(start)=='~')start++;
				end=c.indexOf('(',start);
				temp=c.substring(start, end);
				if(h.containsKey(temp)) 
				{
					l=h.get(temp);
					l.add(a+1+pred);
					pred+=0.1;
				}
				else 
				{
					ArrayList p=new ArrayList<Float>();
					p.add(a+1+pred);
					h.put(temp, p);
					pred+=0.1;

				}
				start=c.indexOf(')', start)+4;

			}
			}

			/*for(int a=0;a<k;a++)
		{ kb[a]= new ArrayList<String>();
			while(temp[a].length()>3)
			{
			t=temp[a].indexOf(')');	
			System.out.println(temp[a].substring(0,t+1));
			kb[a][0]=temp[a].substring(0,t+1);
			System.out.println(kb[a][0]);
			if(temp[a].length()>t+1)
			{
				temp[a]=temp[a].substring(t+3);	
			}
			else break;
			}

		}*/
			//System.out.println(kb[0][1]);
			for(int j=0;j<q;j++)
			System.out.println(query[j]);
		System.out.println();
	for(int j=0;j<k;j++)
			System.out.println(kb.get(j));

	for(Map.Entry<String, ArrayList<Float>> entry : h.entrySet())
	{ System.out.printf("Predicate : %s and Sentence: %s %n", entry.getKey(), entry.getValue()); }
			//System.out.println((int)5.999); 
			//	String[] ab=kb[9].split(" \\| ");
			//System.out.println(ab[0]+" "+ab[1]+" "+ab[2]+" "+ab.length);
			//	System.out.println(query[0]);
			
			for(int j=0;j<q;j++)
			{	
				String q1=negate(query[j]);
				
				tempkb= new ArrayList(kb);
				//System.out.println(q1);
				tempkb.add(q1);
				//System.out.println(tempkb.size());
				int index=tempkb.size()-1;
				temph = new HashMap(h);
			//	for(int j1=0;j1<k+1;j1++)
				//	System.out.println(tempkb.get(j1));

				List l1;
				 start=0;end=0;
			
				
					end=q1.indexOf('(',start);
					temp=q1.substring(start, end);
					if(temph.containsKey(temp)) 
					{
						l=h.get(temp);
						l.add(index+1+0.1f);
					}
					else 
					{
						ArrayList p=new ArrayList<Float>();
						p.add(index+1+0.1f);
						temph.put(temp, p);

					}	
					/*for(Map.Entry<String, ArrayList<Float>> entry : temph.entrySet())
					{ System.out.printf("Predicate : %s and Sentence: %s %n", entry.getKey(), entry.getValue()); }
					*/
			checkQuery(query[j]);
			System.out.println("NEXT");
			stack.clear();
			tempkb.clear();
			temph.clear();
			res.clear();
			}
			for(int j=0;j<q;j++)
			{ System.out.println(ans.get(j));
				pw.println(ans.get(j));
			}
			
		}

		catch(Exception e)
		{

		}
	}
}
