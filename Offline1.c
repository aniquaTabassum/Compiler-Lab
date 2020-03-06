#include<stdio.h>
#include<stdbool.h>
int checkIfSingleComment(FILE *p1);
void removeSingleComment(FILE *p1);
void removeMultipleComment(FILE *p1);
void removeExtraSpaceOrNewLine(FILE *p2);
int provideOneSpace = 0;
void readOutputFile(FILE *p1);

int main()
{

    FILE *p1, *p2;
    char c;

    p1 = fopen("ForOffline.txt","r");
    p2 = fopen("OfflineOutput.txt", "w");
    if(!p1)
    {
        printf("File cannot be opened");
    }
    else
    {
        printf("Input File is: \n");
        while((c = fgetc(p1)) != EOF)
        {

            printf("%c",c);
            if(c != ' ' && c!='\n')
            {

                if(c == '/')
                {

                    int check = checkIfSingleComment(p1);
                    if(check == 1)
                    {
                        removeSingleComment(p1);
                    }
                    else if(check == 2)
                    {
                        removeMultipleComment(p1);
                    }
                    else
                    {
                        provideOneSpace = 0;
                        fputc(c, p2);
                    }
                }
                else
                {
                    provideOneSpace = 0;
                    fputc(c,p2);
                }
            }
            else if(c == ' ' || c =='\n')
            {
                removeExtraSpaceOrNewLine(p2);
            }
        }
    }

    fclose(p1);
    fclose(p2);
    printf("\n \n Output File is : \n");
    readOutputFile(p1);
    return 0;
}

int checkIfSingleComment(FILE *p1)
{
    char check = fgetc(p1);

    if(check == '/')
    {

        return 1;
    }

    else if(check == '*')
    {

        return 2;
    }
    else
    {
        return 3;
    }
}

void removeSingleComment(FILE *p1)
{

    char ignore;
    while((ignore = fgetc(p1))!='\n')
    {

    }
}

void removeMultipleComment(FILE *p1)
{

    char findEnd;
    while((findEnd = fgetc(p1)) != EOF)
    {
        if(findEnd == '*')
        {
            if((findEnd = fgetc(p1)) == '/')
            {
                return;
            }
        }
    }
}

void removeExtraSpaceOrNewLine(FILE *p2)
{
    if(provideOneSpace == 0)
    {
        fputc(' ', p2);
        provideOneSpace = 1;
    }
}


void readOutputFile(FILE *p1)
{
    char c;
    p1 = fopen("OfflineOutput.txt", "r");
    while((c = fgetc(p1)) != EOF)
    {
        printf("%c",c);
    }
}
