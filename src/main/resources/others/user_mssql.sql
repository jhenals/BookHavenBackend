CREATE TABLE [dbo].[utente]
(
    [id] BIGINT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    [username] NVARCHAR(255),
    [email] NVARCHAR(255),
    [password] NVARCHAR(255),
    [firstname] NVARCHAR(255),
    [lastname] NVARCHAR(255),
    [roleName] NVARCHAR(15) NOT NULL DEFAULT 'ROLE_USER',
    CONSTRAINT [FK_User_Role] FOREIGN KEY ([roleName]) REFERENCES [Role] ([Type])
)
