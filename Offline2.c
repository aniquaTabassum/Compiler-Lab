#include<stdio.h>
int main()
{
    FILE *fp1;
    char c;
    char keywords[35][10];
    char operators[30][5];
    char inputFromFile[15];
    int index = 0;
    int flag = 0;
    strcpy(keywords[32],"char");
    strcpy(keywords[1],"int");
    strcpy(keywords[2],"float");
    strcpy(keywords[3],"auto");
    strcpy(keywords[4],"break");
    strcpy(keywords[5],"case");
    strcpy(keywords[6],"const");
    strcpy(keywords[7],"continue");
    strcpy(keywords[8],"default");
    strcpy(keywords[9],"do");
    strcpy(keywords[10],"double");
    strcpy(keywords[11],"else");
    strcpy(keywords[12],"enum");
    strcpy(keywords[13],"extern");
    strcpy(keywords[14],"for");
    strcpy(keywords[15],"goto");
    strcpy(keywords[16],"if");
    strcpy(keywords[17],"long");
    strcpy(keywords[18],"register");
    strcpy(keywords[19],"return");
    strcpy(keywords[20],"short");
    strcpy(keywords[21],"signed");
    strcpy(keywords[22],"sizeof");
    strcpy(keywords[23],"struct");
    strcpy(keywords[24],"switch");
    strcpy(keywords[25],"typedef");
    strcpy(keywords[26],"union");
    strcpy(keywords[27],"unsigned");
    strcpy(keywords[28],"void");
    strcpy(keywords[29],"volatile");
    strcpy(keywords[30],"while");
    strcpy(keywords[31],"static");


    strcpy(operators[29],"++");
    strcpy(operators[1],"--");
    strcpy(operators[2],"+");
    strcpy(operators[3],"-");
    strcpy(operators[4],"*");
    strcpy(operators[5],"/");
    strcpy(operators[6],"%");
    strcpy(operators[7],"<");
    strcpy(operators[8],"<=");
    strcpy(operators[9],">");
    strcpy(operators[10],">!");
    strcpy(operators[11],"==");
    strcpy(operators[12],"!=");
    strcpy(operators[13],"&&");
    strcpy(operators[14],"||");
    strcpy(operators[15],"!");
    strcpy(operators[16],"|");
    strcpy(operators[17],"&");
    strcpy(operators[18],"<<");
    strcpy(operators[19],">>");
    strcpy(operators[20],"~");
    strcpy(operators[21],"^");
    strcpy(operators[22],"=");
    strcpy(operators[23],"+=");
    strcpy(operators[24],"-=");
    strcpy(operators[25],"*=");
    strcpy(operators[26],"/=");
    strcpy(operators[27],"%=");
    strcpy(operators[28],"?:");


    for(int i = 0; i<15; i++)
    {
        inputFromFile[i] = '\0';
    }
    fp1 = fopen("OfflineOutput.txt","r");
    if(!fp1)
    {
        printf("cannot open this file \n");
    }
    else
    {
        while((c = fgetc(fp1))!=EOF)
        {


            if(c != ' ')
            {
                inputFromFile[index] = c;
                index+=1;
            }

            else if( c == ' ')
            {

                //check if keyword
                inputFromFile[index] = '\0';
                for(int j = 0; j<35; j++)
                {
                    if(strcmp(keywords[j], inputFromFile) == 0)
                    {
                        printf("[char %s]\n", keywords[j]);
                        flag = 1;

                        break;
                    }

                }


                if(flag == 0)
                {

                    if(inputFromFile[0] == ';')
                    {
                        printf("[sep %c]\n",inputFromFile[0]);
                        index = 0;
                        flag = 1;
                    }
                }


                if(flag == 0)
                {
                    inputFromFile[index] = '\0';
                    for(int j = 1; j<30; j++)
                    {
                        if(strcmp(operators[j], inputFromFile) == 0)
                        {
                            printf("[operator %s]\n", operators[j]);
                            flag = 1;
                            index = 0;
                            break;
                        }

                    }
                }

                if(flag == 0)
                {

                    //if(('a' <= inputFromFile[0] && inputFromFile[0] <= 'z') || (inputFromFile[0] == '_') ||('A' <= inputFromFile[0] && inputFromFile[0] <= 'Z'))
                    if(isalnum(inputFromFile == 1) || (inputFromFile[0] == '_'))
                    {

                        int iterator = 1;
                        for(; inputFromFile[iterator] != '\0'; iterator++)
                        {
                            if(isdigit(inputFromFile[iterator]) || (inputFromFile[iterator] == '_') || isalpha(inputFromFile[iterator]))
                            {
                                flag = 1;
                            }
                            else
                            {
                                flag = 0;
                                break;
                            }
                        }

                        if(flag == 1)
                        {
                            printf("[id %s]\n", inputFromFile);

                        }
                    }
                }
                for(int j =0; j<15; j++)
                    inputFromFile[j] = '\0';
                flag = 0;
                index = 0;
            }
        }
    }


}
