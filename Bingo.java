import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Bingo {
    int[] drawnNumbers = {46,12,57,37,14,78,31,71,87,52,64,97,10,35,54,36,27,84,80,94,99,22,0,11,30,44,86,59,66,7,90,21,51,53,92,8,76,41,39,77,42,88,29,24,60,17,68,13,79,67,50,82,25,61,20,16,6,3,81,19,85,9,28,56,75,96,2,26,1,62,33,63,32,73,18,48,43,65,98,5,91,69,47,4,38,23,49,34,55,83,93,45,72,95,40,15,58,74,70,89};
    ArrayList<Grid> Grids = new ArrayList<Grid>();

    public Bingo() {
        try {
            File myObj = new File("filename.txt");
            Scanner myReader = new Scanner(myObj);

            Grid temp = new Grid();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.length() != 0)
                {
                    String[] splittedData = data.trim().split("\\s+");
                    int[] row = new int[5];
                    for (int a = 0;a < 5;a++) {
                        row[a] = Integer.parseInt(splittedData[a]);
                    }
                    System.out.println(Arrays.toString(row));
                    temp.InsertRow(row);
                }
                else
                {
                    temp.CreateColumns();
                    Grids.add(temp);
                    System.out.println("Another Grid !!");
                    temp = new Grid();
                }

            }
            temp.CreateColumns();
            Grids.add(temp);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int GridSize = Grids.size();
        System.out.println("Bingo successfully uploaded!!");
        BingoCheck();
        System.out.println("Bingo successfully checked!!");
        WinnerTicket();

    }

    void BingoCheck(){
        for (Grid tempGrid:Grids) {
            int[] verticalBingos = new int[5];//dikey
            int[] horizontalBingos = new int[5];//yatay
            boolean BingoTrue = false;

                int place = 0;
                for (int newNum:drawnNumbers) {
                    for (int x = 0; x<5 && BingoTrue == false ; x++) //satir
                    {
                        for (int y = 0;y<5 && BingoTrue == false;y++)//sira
                        {
                            if(tempGrid.rows[x][y] == newNum)
                            {
                                horizontalBingos[x]++;
                                if(horizontalBingos[x]==5)
                                {
                                    tempGrid.bingoPlacementNum = newNum;
                                    tempGrid.placement = place;
                                    BingoTrue = true;
                                }
                            }
                        }
                    }
                    for (int x = 0; x<5 && BingoTrue == false ; x++) //satir
                    {
                        for (int y = 0;y<5 && BingoTrue == false;y++)//sira
                        {
                            if(tempGrid.columns[x][y] == newNum)
                            {
                                verticalBingos[x]++;
                                if(verticalBingos[x]==5)
                                {
                                    tempGrid.bingoPlacementNum = newNum;
                                    tempGrid.placement = place;
                                    BingoTrue= true;
                                }
                            }
                        }
                    }

                if (BingoTrue == true)
                    break;
                place++;
                }

        }
    }

    void WinnerTicket()
    {
        Grid startGrid = new Grid(-1);
        int place = 0;
        int lastPlace = 0;
        for (Grid temp:Grids) {
            if(startGrid.placement < temp.placement)
            {
                startGrid = temp;
                lastPlace = place;
            }
            place++;
        }
        //cross all checked numbers


        ArrayList<Integer> unmarkedList = startGrid.ReturnList();
        System.out.println("Bingo karti numarasi : "+lastPlace);

        Grid finalStartGrid = startGrid;

        unmarkedList.removeIf(n -> (n == finalStartGrid.bingoPlacementNum));
        System.out.println(unmarkedList);
        for (int num:drawnNumbers) {

            if(num == finalStartGrid.bingoPlacementNum) {
                unmarkedList.remove(Integer.valueOf(finalStartGrid.bingoPlacementNum));
                System.out.println("Removed p: "+finalStartGrid.bingoPlacementNum);
                break;
            }

            else if (unmarkedList.contains(num)) {
                unmarkedList.remove(Integer.valueOf(num));
                System.out.println("Removed : "+num);
            }
        }
        int total = 0;
        for (int added:unmarkedList)
            total += added;
        System.out.println(total*finalStartGrid.bingoPlacementNum);

    }
}

class Grid
{
    int[][] rows = new int[5][5];
    int[][] columns = new int[5][5];
    int bingoPlacementNum,placement;
    public Grid(int placement) {
        for (int x = 0;x<5;x++)
        {
            rows[x][0] = -1;
        }
        bingoPlacementNum = -1;
        this.placement = placement;
    }
    public Grid() {
        for (int x = 0;x<5;x++)
        {
            rows[x][0] = -1;
        }
        bingoPlacementNum = -1;
        this.placement = -1;
    }
    void InsertRow(int[] row)
    {
        for (int a = 0; a < 5;a++)
        {
            int x = rows[a].length;
            if(rows[a][0] == -1) //empty
            {
                rows[a] = Arrays.copyOf(row, 5);
                break;
            }
        }
    }

    void CreateColumns()
    {
        for (int x = 0;x<5;x++)
            for (int y = 0;y<5;y++)
                columns[x][y]=rows[y][x];
    }

    ArrayList<Integer> ReturnList()
    {
        ArrayList<Integer> totalList = new ArrayList<Integer>();

        for (int row[]:rows) {
            for (int num:row) {
                totalList.add(num);
            }
        }

        return totalList;
    }
}
