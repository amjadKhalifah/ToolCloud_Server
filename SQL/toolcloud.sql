USE [TOOLCLOUD_DB]
GO
/****** Object:  Table [dbo].[COMPANY]    Script Date: 06-Feb-15 7:49:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[COMPANY](
	[COMPANY_ID] [varchar](50) NOT NULL,
	[NAME] [varchar](100) NOT NULL,
 CONSTRAINT [PK_COMPANY] PRIMARY KEY CLUSTERED 
(
	[COMPANY_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[INTAKE]    Script Date: 06-Feb-15 7:49:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[INTAKE](
	[INTAKE_ID] [varchar](50) NOT NULL,
	[NAME] [varchar](100) NULL,
	[LENGTH] [varchar](50) NULL,
	[HEIGHT] [varchar](50) NULL,
	[MACHINE_ID] [varchar](50) NULL,
 CONSTRAINT [PK_INTAKE] PRIMARY KEY CLUSTERED 
(
	[INTAKE_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MACHINE]    Script Date: 06-Feb-15 7:49:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MACHINE](
	[MACHINE_ID] [varchar](50) NOT NULL,
	[NAME] [varchar](100) NULL,
	[DER] [varchar](50) NULL,
	[COMPANY_ID] [varchar](50) NULL,
 CONSTRAINT [PK_MACHINE] PRIMARY KEY CLUSTERED 
(
	[MACHINE_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TOOL]    Script Date: 06-Feb-15 7:49:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TOOL](
	[TOOL_ID] [varchar](50) NOT NULL,
	[NAME] [varchar](50) NULL,
	[LENGTH] [varchar](50) NULL,
	[HEIGHT] [varchar](50) NULL,
	[MACHINE_ID] [varchar](50) NULL,
	[INTAKE_ID] [varchar](50) NULL,
 CONSTRAINT [PK_TOOL] PRIMARY KEY CLUSTERED 
(
	[TOOL_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[COMPANY]  WITH CHECK ADD  CONSTRAINT [FK_COMPANY_COMPANY] FOREIGN KEY([COMPANY_ID])
REFERENCES [dbo].[COMPANY] ([COMPANY_ID])
GO
ALTER TABLE [dbo].[COMPANY] CHECK CONSTRAINT [FK_COMPANY_COMPANY]
GO
ALTER TABLE [dbo].[INTAKE]  WITH CHECK ADD  CONSTRAINT [FK_INTAKE_MACHINE] FOREIGN KEY([MACHINE_ID])
REFERENCES [dbo].[MACHINE] ([MACHINE_ID])
GO
ALTER TABLE [dbo].[INTAKE] CHECK CONSTRAINT [FK_INTAKE_MACHINE]
GO
ALTER TABLE [dbo].[MACHINE]  WITH CHECK ADD  CONSTRAINT [FK_MACHINE_COMPANY] FOREIGN KEY([COMPANY_ID])
REFERENCES [dbo].[COMPANY] ([COMPANY_ID])
GO
ALTER TABLE [dbo].[MACHINE] CHECK CONSTRAINT [FK_MACHINE_COMPANY]
GO
ALTER TABLE [dbo].[TOOL]  WITH CHECK ADD  CONSTRAINT [FK_TOOL_INTAKE] FOREIGN KEY([INTAKE_ID])
REFERENCES [dbo].[INTAKE] ([INTAKE_ID])
GO
ALTER TABLE [dbo].[TOOL] CHECK CONSTRAINT [FK_TOOL_INTAKE]
GO
ALTER TABLE [dbo].[TOOL]  WITH NOCHECK ADD  CONSTRAINT [FK_TOOL_MACHINE] FOREIGN KEY([TOOL_ID])
REFERENCES [dbo].[MACHINE] ([MACHINE_ID])
GO
ALTER TABLE [dbo].[TOOL] NOCHECK CONSTRAINT [FK_TOOL_MACHINE]
GO
