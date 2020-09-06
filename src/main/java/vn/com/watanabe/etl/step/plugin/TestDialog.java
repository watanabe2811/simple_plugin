package vn.com.watanabe.etl.step.plugin;

import java.nio.charset.Charset;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.util.Utils;
import org.pentaho.di.core.Props;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.fileinput.FileInputList;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.vfs.KettleVFS;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.sdk.samples.steps.demo.DemoStepMeta;
import org.pentaho.di.sdk.samples.steps.demo.XMLParserDialogField;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.TransPreviewFactory;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.dialog.EnterNumberDialog;
import org.pentaho.di.ui.core.dialog.EnterSelectionDialog;
import org.pentaho.di.ui.core.dialog.EnterStringDialog;
import org.pentaho.di.ui.core.dialog.EnterTextDialog;
import org.pentaho.di.ui.core.dialog.ErrorDialog;
import org.pentaho.di.ui.core.dialog.PreviewRowsDialog;
import org.pentaho.di.ui.core.events.dialog.FilterType;
import org.pentaho.di.ui.core.events.dialog.SelectionAdapterFileDialogTextVar;
import org.pentaho.di.ui.core.events.dialog.SelectionAdapterOptions;
import org.pentaho.di.ui.core.events.dialog.SelectionOperation;
import org.pentaho.di.ui.core.widget.ColumnInfo;
import org.pentaho.di.ui.core.widget.TableView;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.dialog.TransPreviewProgressDialog;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class TestDialog extends BaseStepDialog implements StepDialogInterface {
    private static String EMPTY_FIELDS = "<EMPTY>";

    private CTabFolder wTabFolder;
    private FormData fdTabFolder;

    private CTabItem wFileTab, wContentTab, wFieldsTab;

    private Composite wFileComp, wContentComp, wFieldsComp;
    private FormData fdFileComp, fdContentComp, fdFieldsComp;

    private Label wlFilename, wlXMLIsAFile;
    private Button wbbFilename; // Browse: add file or directory
    private Button wbdFilename; // Delete
    private Button wbeFilename; // Edit
    private Button wbaFilename; // Add or change
    private TextVar wFilename;
    private FormData fdlFilename, fdbFilename, fdbdFilename, fdbeFilename, fdbaFilename, fdFilename;

    private Label wlFilenameList;
    private TableView wFilenameList;
    private FormData fdlFilenameList, fdFilenameList;

    private Label wlFilemask;
    private TextVar wFilemask;
    private FormData fdlFilemask, fdFilemask;

    private Button wbShowFiles;
    private FormData fdbShowFiles;

    private Label wluseToken;
    private Button wuseToken;
    private FormData fdluseToken, fduseToken;

    private FormData fdlXMLField, fdlXMLStreamField, fdlXMLIsAFile;
    private FormData fdXMLField, fdXSDFileField;
    private FormData fdOutputField, fdXMLIsAFile, fdAdditionalFields, fdAddFileResult, fdXmlConf;
    private Label wlXMLField, wlXmlStreamField;
    private CCombo wXMLField;
    private Button wXMLStreamField, wXMLIsAFile;

    private Label wlInclFilename;
    private Button wInclFilename, wAddResult;
    private FormData fdlInclFilename, fdInclFilename, fdAddResult, fdlAddResult;

    private Label wlNameSpaceAware;
    private Button wNameSpaceAware;
    private FormData fdlNameSpaceAware, fdNameSpaceAware;

    private Label wlreadUrl;
    private Button wreadUrl;
    private FormData fdlreadUrl, fdreadUrl;

    private Label wlIgnoreComment;
    private Button wIgnoreComment;
    private FormData fdlIgnoreComment, fdIgnoreComment;

    private Label wlValidating;
    private Button wValidating;
    private FormData fdlValidating, fdValidating;

    private Label wlInclFilenameField;
    private TextVar wInclFilenameField;
    private FormData fdlInclFilenameField, fdInclFilenameField;

    private Label wlInclRownum, wlAddResult;
    private Button wInclRownum;
    private FormData fdlInclRownum, fdRownum;

    private Label wlInclRownumField;
    private TextVar wInclRownumField;
    private FormData fdlInclRownumField, fdInclRownumField;

    private Label wlLimit;
    private Text wLimit;
    private FormData fdlLimit, fdLimit;

    private Label wlLoopXPath;
    private TextVar wLoopXPath;
    private FormData fdlLoopXPath, fdLoopXPath;

    private Label wlPrunePath;
    private TextVar wPrunePath;
    private FormData fdlPrunePath, fdPrunePath;

    private Label wlEncoding;
    private CCombo wEncoding;
    private FormData fdlEncoding, fdEncoding;

    private TableView wFields;
    private FormData fdFields;

    private Group wOutputField;
    private Group wAdditionalFields;
    private Group wAddFileResult;
    private Group wXmlConf;

    private Button wbbLoopPathList;
    private FormData fdbLoopPathList;

    private Label wlExcludeFilemask;
    private TextVar wExcludeFilemask;
    private FormData fdlExcludeFilemask, fdExcludeFilemask;

    // ignore empty files flag
    private Label wlIgnoreEmptyFile;
    private Button wIgnoreEmptyFile;
    private FormData fdlIgnoreEmptyFile, fdIgnoreEmptyFile;

    // do not fail if no files?
    private Label wldoNotFailIfNoFile;
    private Button wdoNotFailIfNoFile;
    private FormData fdldoNotFailIfNoFile, fddoNotFailIfNoFile;

    private CTabItem wAdditionalFieldsTab;
    private Composite wAdditionalFieldsComp;
    private FormData fdAdditionalFieldsComp;

    private Label wlShortFileFieldName;
    private FormData fdlShortFileFieldName;
    private TextVar wShortFileFieldName;
    private FormData fdShortFileFieldName;
    private Label wlPathFieldName;
    private FormData fdlPathFieldName;
    private TextVar wPathFieldName;
    private FormData fdPathFieldName;

    private Label wlIsHiddenName;
    private FormData fdlIsHiddenName;
    private TextVar wIsHiddenName;
    private FormData fdIsHiddenName;
    private Label wlLastModificationTimeName;
    private FormData fdlLastModificationTimeName;
    private TextVar wLastModificationTimeName;
    private FormData fdLastModificationTimeName;
    private Label wlUriName;
    private FormData fdlUriName;
    private TextVar wUriName;
    private FormData fdUriName;
    private Label wlRootUriName;
    private FormData fdlRootUriName;
    private TextVar wRootUriName;
    private FormData fdRootUriName;
    private Label wlExtensionFieldName;
    private FormData fdlExtensionFieldName;
    private TextVar wExtensionFieldName;
    private FormData fdExtensionFieldName;
    private Label wlSizeFieldName;
    private FormData fdlSizeFieldName;
    private TextVar wSizeFieldName;
    private FormData fdSizeFieldName;

    private int middle;
    private int margin;
    private ModifyListener lsMod;

    private DemoStepMeta input;

    private boolean gotEncodings = false;

    private String XMLSource = null;

    ////////////////////////////////////////////
    private static Class<?> PKG = TestDialog.class; // for i18n purposes, needed by Translator2!!
    private static String DEFAULT_PREFIX="GetXMLDataDialog";
    //////////////////////////////////////////////////
    public static final int[] dateLengths = new int[] { 23, 19, 14, 10, 10, 10, 10, 8, 8, 8, 8, 6, 6 };

    public TestDialog(final Shell parent, final BaseStepMeta baseStepMeta, final TransMeta transMeta, final String stepname) {
        super(parent, baseStepMeta, transMeta, stepname);
        // TODO Auto-generated constructor stub
        this.input = (DemoStepMeta) baseStepMeta;
    }

    @Override
    public String open() {
        // TODO Auto-generated method stub
        final Display display = initDialog();

        // Stepname line
        genStepNameLine();

        // ////////////////////////
        // START OF FILE TAB ///
        // ////////////////////////
        genFileTab();

        // ///////////////////////////////////////////////////////////
        // / END OF FILE TAB
        // ///////////////////////////////////////////////////////////

        // ////////////////////////
        // START OF CONTENT TAB///
        // /
        genContentTab();

        // ///////////////////////////////////////////////////////////
        // / END OF CONTENT TAB
        // ///////////////////////////////////////////////////////////

        // Fields tab...
        //
        genFieldsTabs();

        // addAdditionalFieldsTab();

        setupLayouTabs();

        genButtons();

        wStepname.addSelectionListener(lsDef);
        wLimit.addSelectionListener(lsDef);
        wInclRownumField.addSelectionListener(lsDef);
        wInclFilenameField.addSelectionListener(lsDef);

        // // Add the file to the list of files...
        // final SelectionAdapter selA = new SelectionAdapter() {
        //     public void widgetSelected(final SelectionEvent arg0) {
        //         wFilenameList.add(new String[] { wFilename.getText(), wFilemask.getText(), wExcludeFilemask.getText(),
        //                 GetXMLDataMeta.RequiredFilesCode[0], GetXMLDataMeta.RequiredFilesCode[0] });
        //         wFilename.setText("");
        //         wFilemask.setText("");
        //         wExcludeFilemask.setText("");
        //         wFilenameList.removeEmptyRows();
        //         wFilenameList.setRowNums();
        //         wFilenameList.optWidth(true);
        //     }
        // };
        // wbaFilename.addSelectionListener(selA);
        // wFilename.addSelectionListener(selA);

        // // Delete files from the list of files...
        // wbdFilename.addSelectionListener(new SelectionAdapter() {
        //     public void widgetSelected(final SelectionEvent arg0) {
        //         final int[] idx = wFilenameList.getSelectionIndices();
        //         wFilenameList.remove(idx);
        //         wFilenameList.removeEmptyRows();
        //         wFilenameList.setRowNums();
        //     }
        // });

        // // Edit the selected file & remove from the list...
        // wbeFilename.addSelectionListener(new SelectionAdapter() {
        //     public void widgetSelected(final SelectionEvent arg0) {
        //         final int idx = wFilenameList.getSelectionIndex();
        //         if (idx >= 0) {
        //             final String[] string = wFilenameList.getItem(idx);
        //             wFilename.setText(string[0]);
        //             wFilemask.setText(string[1]);
        //             wExcludeFilemask.setText(string[2]);
        //             wFilenameList.remove(idx);
        //         }
        //         wFilenameList.removeEmptyRows();
        //         wFilenameList.setRowNums();
        //     }
        // });

        // // Show the files that are selected at this time...
        // wbShowFiles.addSelectionListener(new SelectionAdapter() {
        //     public void widgetSelected(final SelectionEvent e) {
        //         try {
        //             final GetXMLDataMeta tfii = new GetXMLDataMeta();
        //             getInfo(tfii);
        //             final FileInputList fileInputList = tfii.getFiles(transMeta);
        //             final String[] files = fileInputList.getFileStrings();
        //             if (files != null && files.length > 0) {
        //                 final EnterSelectionDialog esd = new EnterSelectionDialog(shell, files,
        //                         BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilesReadSelection.DialogTitle"),
        //                         BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilesReadSelection.DialogMessage"));
        //                 esd.setViewOnly();
        //                 esd.open();
        //             } else {
        //                 final MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
        //                 mb.setMessage(BaseMessages.getString(PKG, DEFAULT_PREFIX+".NoFileFound.DialogMessage"));
        //                 mb.setText(BaseMessages.getString(PKG, "System.Dialog.Error.Title"));
        //                 mb.open();
        //             }
        //         } catch (final KettleException ex) {
        //             new ErrorDialog(shell, BaseMessages.getString(PKG, DEFAULT_PREFIX+".ErrorParsingData.DialogTitle"),
        //                     BaseMessages.getString(PKG, DEFAULT_PREFIX+".ErrorParsingData.DialogMessage"), ex);
        //         }
        //     }
        // });
        // // Enable/disable the right fields to allow a filename to be added to each
        // // row...
        // wInclFilename.addSelectionListener(new SelectionAdapter() {
        //     public void widgetSelected(final SelectionEvent e) {
        //         setIncludeFilename();
        //     }
        // });

        // // Enable/disable the right fields to allow a row number to be added to each
        // // row...
        // wInclRownum.addSelectionListener(new SelectionAdapter() {
        //     public void widgetSelected(final SelectionEvent e) {
        //         setIncludeRownum();
        //     }
        // });

        // // Whenever something changes, set the tooltip to the expanded version of the
        // // filename:
        // wFilename.addModifyListener(new ModifyListener() {
        //     public void modifyText(final ModifyEvent e) {
        //         wFilename.setToolTipText(wFilename.getText());
        //     }
        // });

        // wbbFilename.addSelectionListener(new SelectionAdapterFileDialogTextVar(log, wFilename, transMeta,
        //         new SelectionAdapterOptions(SelectionOperation.FILE_OR_FOLDER,
        //                 new FilterType[] { FilterType.ALL, FilterType.XML }, FilterType.XML)));

        // Detect X or ALT-F4 or something that kills this window...
        shell.addShellListener(new ShellAdapter() {
            public void shellClosed(final ShellEvent e) {
                cancel();
            }
        });

        wTabFolder.setSelection(0);

        // Set the shell size, based upon previous time...
        setSize();
        // getData(input);
        ActiveXmlStreamField();
        // setIncludeFilename();
        // setIncludeRownum();
        input.setChanged(changed);
        wFields.optWidth(true);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return stepname;
    }

    private void genButtons() {
        wOK = new Button(shell, SWT.PUSH);
        wOK.setText(BaseMessages.getString(PKG, "System.Button.OK"));

        wPreview = new Button(shell, SWT.PUSH);
        wPreview.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Button.PreviewRows"));

        wCancel = new Button(shell, SWT.PUSH);
        wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));

        setButtonPositions(new Button[] { wOK, wPreview, wCancel }, margin, wTabFolder);

        // Add listeners
        lsOK = new Listener() {
            public void handleEvent(final Event e) {
                ok();
            }
        };
        // lsGet = new Listener() {
        //     public void handleEvent(final Event e) {
        //         get();
        //     }
        // };
        // lsPreview = new Listener() {
        //     public void handleEvent(final Event e) {
        //         preview();
        //     }
        // };
        lsCancel = new Listener() {
            public void handleEvent(final Event e) {
                cancel();
            }
        };

        wOK.addListener(SWT.Selection, lsOK);
        wGet.addListener(SWT.Selection, lsGet);
        wPreview.addListener(SWT.Selection, lsPreview);
        wCancel.addListener(SWT.Selection, lsCancel);

        lsDef = new SelectionAdapter() {
            public void widgetDefaultSelected(final SelectionEvent e) {
                ok();
            }
        };
    }

    private void setupLayouTabs() {
        fdTabFolder = new FormData();
        fdTabFolder.left = new FormAttachment(0, 0);
        fdTabFolder.top = new FormAttachment(wStepname, margin);
        fdTabFolder.right = new FormAttachment(100, 0);
        fdTabFolder.bottom = new FormAttachment(100, -50);
        wTabFolder.setLayoutData(fdTabFolder);
    }

    private void genFieldsTabs() {
        wFieldsTab = new CTabItem(wTabFolder, SWT.NONE);
        wFieldsTab.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Fields.Tab"));

        final FormLayout fieldsLayout = new FormLayout();
        fieldsLayout.marginWidth = Const.FORM_MARGIN;
        fieldsLayout.marginHeight = Const.FORM_MARGIN;

        wFieldsComp = new Composite(wTabFolder, SWT.NONE);
        wFieldsComp.setLayout(fieldsLayout);
        props.setLook(wFieldsComp);

        wGet = new Button(wFieldsComp, SWT.PUSH);
        wGet.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".GetFields.Button"));
        fdGet = new FormData();
        fdGet.left = new FormAttachment(50, 0);
        fdGet.bottom = new FormAttachment(100, 0);
        wGet.setLayoutData(fdGet);

        // final int FieldsRows = input.getInputFields().length;
        int FieldsRows = 0;
        final ColumnInfo[] colinf = new ColumnInfo[] {
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Name.Column"),
                        ColumnInfo.COLUMN_TYPE_TEXT, false),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.XPath.Column"),
                        ColumnInfo.COLUMN_TYPE_TEXT, false),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Element.Column"),
                        ColumnInfo.COLUMN_TYPE_CCOMBO, XMLParserDialogField.ElementTypeDesc, true),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.ResultType.Column"),
                        ColumnInfo.COLUMN_TYPE_CCOMBO, XMLParserDialogField.ResultTypeDesc, true),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Type.Column"),
                        ColumnInfo.COLUMN_TYPE_CCOMBO, ValueMeta.getTypes(), true),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Format.Column"),
                        ColumnInfo.COLUMN_TYPE_FORMAT, 4),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Length.Column"),
                        ColumnInfo.COLUMN_TYPE_TEXT, false),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Precision.Column"),
                        ColumnInfo.COLUMN_TYPE_TEXT, false),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Currency.Column"),
                        ColumnInfo.COLUMN_TYPE_TEXT, false),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Decimal.Column"),
                        ColumnInfo.COLUMN_TYPE_TEXT, false),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Group.Column"),
                        ColumnInfo.COLUMN_TYPE_TEXT, false),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.TrimType.Column"),
                        ColumnInfo.COLUMN_TYPE_CCOMBO, XMLParserDialogField.trimTypeDesc, true),
                new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Repeat.Column"),
                        ColumnInfo.COLUMN_TYPE_CCOMBO, new String[] { BaseMessages.getString(PKG, "System.Combo.Yes"),
                                BaseMessages.getString(PKG, "System.Combo.No") },
                        true),

        };

        colinf[0].setUsingVariables(true);
        colinf[0].setToolTip(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.Name.Column.Tooltip"));
        colinf[1].setUsingVariables(true);
        colinf[1].setToolTip(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FieldsTable.XPath.Column.Tooltip"));

        wFields = new TableView(transMeta, wFieldsComp, SWT.FULL_SELECTION | SWT.MULTI, colinf, FieldsRows, lsMod,
                props);

        fdFields = new FormData();
        fdFields.left = new FormAttachment(0, 0);
        fdFields.top = new FormAttachment(0, 0);
        fdFields.right = new FormAttachment(100, 0);
        fdFields.bottom = new FormAttachment(wGet, -margin);
        wFields.setLayoutData(fdFields);

        fdFieldsComp = new FormData();
        fdFieldsComp.left = new FormAttachment(0, 0);
        fdFieldsComp.top = new FormAttachment(0, 0);
        fdFieldsComp.right = new FormAttachment(100, 0);
        fdFieldsComp.bottom = new FormAttachment(100, 0);
        wFieldsComp.setLayoutData(fdFieldsComp);

        wFieldsComp.layout();
        wFieldsTab.setControl(wFieldsComp);
    }
    private void cancel() {
        stepname = null;
        input.setChanged( changed );
        dispose();
      }
    
      public void dispose() {
        XMLSource = null;
        super.dispose();
      }
    
      private void ok() {
        try {
          getInfo( input );
        } catch ( KettleException e ) {
          new ErrorDialog( shell, BaseMessages.getString( PKG, "GetXMLDataDialog.ErrorParsingData.DialogTitle" ),
              BaseMessages.getString( PKG, "GetXMLDataDialog.ErrorParsingData.DialogMessage" ), e );
        }
        dispose();
      }
      private void getInfo( DemoStepMeta in ) throws KettleException {
        // stepname = wStepname.getText(); // return value
    
        // // copy info to TextFileInputMeta class (input)
        // in.setRowLimit( Const.toLong( wLimit.getText(), 0L ) );
        // in.setPrunePath( wPrunePath.getText() );
        // in.setLoopXPath( wLoopXPath.getText() );
        // in.setEncoding( wEncoding.getText() );
        // in.setFilenameField( wInclFilenameField.getText() );
        // in.setRowNumberField( wInclRownumField.getText() );
        // in.setAddResultFile( wAddResult.getSelection() );
        // in.setIncludeFilename( wInclFilename.getSelection() );
        // in.setIncludeRowNumber( wInclRownum.getSelection() );
        // in.setNamespaceAware( wNameSpaceAware.getSelection() );
        // in.setReadUrl( wreadUrl.getSelection() );
        // in.setIgnoreComments( wIgnoreComment.getSelection() );
        // in.setValidating( wValidating.getSelection() );
        // in.setuseToken( wuseToken.getSelection() );
        // in.setIgnoreEmptyFile( wIgnoreEmptyFile.getSelection() );
        // in.setdoNotFailIfNoFile( wdoNotFailIfNoFile.getSelection() );
    
        // in.setInFields( wXMLStreamField.getSelection() );
        // in.setIsAFile( wXMLIsAFile.getSelection() );
        // in.setXMLField( wXMLField.getText() );
    
        // int nrFiles = wFilenameList.getItemCount();
        // int nrFields = wFields.nrNonEmpty();
    
        // in.allocate( nrFiles, nrFields );
        // in.setFileName( wFilenameList.getItems( 0 ) );
        // in.setFileMask( wFilenameList.getItems( 1 ) );
        // in.setExcludeFileMask( wFilenameList.getItems( 2 ) );
        // in.setFileRequired( wFilenameList.getItems( 3 ) );
        // in.setIncludeSubFolders( wFilenameList.getItems( 4 ) );
    
        // for ( int i = 0; i < nrFields; i++ ) {
        //   GetXMLDataField field = new GetXMLDataField();
    
        //   TableItem item = wFields.getNonEmpty( i );
    
        //   field.setName( item.getText( 1 ) );
        //   field.setXPath( item.getText( 2 ) );
        //   field.setElementType( GetXMLDataField.getElementTypeByDesc( item.getText( 3 ) ) );
        //   field.setResultType( GetXMLDataField.getResultTypeByDesc( item.getText( 4 ) ) );
        //   field.setType( ValueMeta.getType( item.getText( 5 ) ) );
        //   field.setFormat( item.getText( 6 ) );
        //   field.setLength( Const.toInt( item.getText( 7 ), -1 ) );
        //   field.setPrecision( Const.toInt( item.getText( 8 ), -1 ) );
        //   field.setCurrencySymbol( item.getText( 9 ) );
        //   field.setDecimalSymbol( item.getText( 10 ) );
        //   field.setGroupSymbol( item.getText( 11 ) );
        //   field.setTrimType( GetXMLDataField.getTrimTypeByDesc( item.getText( 12 ) ) );
        //   field.setRepeated( BaseMessages.getString( PKG, "System.Combo.Yes" ).equalsIgnoreCase( item.getText( 13 ) ) );
    
        //   // CHECKSTYLE:Indentation:OFF
        //   in.getInputFields()[i] = field;
        // }
        // in.setShortFileNameField( wShortFileFieldName.getText() );
        // in.setPathField( wPathFieldName.getText() );
        // in.setIsHiddenField( wIsHiddenName.getText() );
        // in.setLastModificationDateField( wLastModificationTimeName.getText() );
        // in.setUriField( wUriName.getText() );
        // in.setRootUriField( wRootUriName.getText() );
        // in.setExtensionField( wExtensionFieldName.getText() );
        // in.setSizeField( wSizeFieldName.getText() );
      }
    

    private void genContentTab() {
        startContentTab();

        // ///////////////////////////////
        // START OF XmlConf Field GROUP //
        // ///////////////////////////////

        genXmlConfFieldGroup();

        // ///////////////////////////////////////////////////////////
        // / END OF XmlConf Field GROUP
        // ///////////////////////////////////////////////////////////

        // ///////////////////////////////
        // START OF Additional Fields GROUP //
        // ///////////////////////////////
        genAdditionalFieldsGroup();

        // ///////////////////////////////////////////////////////////
        // / END OF Additional Fields GROUP
        // ///////////////////////////////////////////////////////////

        // ///////////////////////////////
        // START OF AddFileResult GROUP //
        // ///////////////////////////////

        genAddFileResultGroup();

        // ///////////////////////////////////////////////////////////
        // / END OF AddFileResult GROUP
        // ///////////////////////////////////////////////////////////

        fdContentComp = new FormData();
        fdContentComp.left = new FormAttachment(0, 0);
        fdContentComp.top = new FormAttachment(0, 0);
        fdContentComp.right = new FormAttachment(100, 0);
        fdContentComp.bottom = new FormAttachment(100, 0);
        wContentComp.setLayoutData(fdContentComp);

        wContentComp.layout();
        wContentTab.setControl(wContentComp);
    }

    private void genAddFileResultGroup() {
        wAddFileResult = new Group(wContentComp, SWT.SHADOW_NONE);
        props.setLook(wAddFileResult);
        wAddFileResult.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".wAddFileResult.Label"));

        final FormLayout AddFileResultgroupLayout = new FormLayout();
        AddFileResultgroupLayout.marginWidth = 10;
        AddFileResultgroupLayout.marginHeight = 10;
        wAddFileResult.setLayout(AddFileResultgroupLayout);

        wlAddResult = new Label(wAddFileResult, SWT.RIGHT);
        wlAddResult.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".AddResult.Label"));
        props.setLook(wlAddResult);
        fdlAddResult = new FormData();
        fdlAddResult.left = new FormAttachment(0, 0);
        fdlAddResult.top = new FormAttachment(wAdditionalFields, margin);
        fdlAddResult.right = new FormAttachment(middle, -margin);
        wlAddResult.setLayoutData(fdlAddResult);
        wAddResult = new Button(wAddFileResult, SWT.CHECK);
        props.setLook(wAddResult);
        wAddResult.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".AddResult.Tooltip"));
        fdAddResult = new FormData();
        fdAddResult.left = new FormAttachment(middle, 0);
        fdAddResult.top = new FormAttachment(wAdditionalFields, margin);
        wAddResult.setLayoutData(fdAddResult);

        fdAddFileResult = new FormData();
        fdAddFileResult.left = new FormAttachment(0, margin);
        fdAddFileResult.top = new FormAttachment(wAdditionalFields, margin);
        fdAddFileResult.right = new FormAttachment(100, -margin);
        wAddFileResult.setLayoutData(fdAddFileResult);
    }

    private void genAdditionalFieldsGroup() {
        wAdditionalFields = new Group(wContentComp, SWT.SHADOW_NONE);
        props.setLook(wAdditionalFields);
        wAdditionalFields.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".wAdditionalFields.Label"));

        final FormLayout AdditionalFieldsgroupLayout = new FormLayout();
        AdditionalFieldsgroupLayout.marginWidth = 10;
        AdditionalFieldsgroupLayout.marginHeight = 10;
        wAdditionalFields.setLayout(AdditionalFieldsgroupLayout);

        wlInclFilename = new Label(wAdditionalFields, SWT.RIGHT);
        wlInclFilename.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".InclFilename.Label"));
        props.setLook(wlInclFilename);
        fdlInclFilename = new FormData();
        fdlInclFilename.left = new FormAttachment(0, 0);
        fdlInclFilename.top = new FormAttachment(wXmlConf, 4 * margin);
        fdlInclFilename.right = new FormAttachment(middle, -margin);
        wlInclFilename.setLayoutData(fdlInclFilename);
        wInclFilename = new Button(wAdditionalFields, SWT.CHECK);
        props.setLook(wInclFilename);
        wInclFilename.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".InclFilename.Tooltip"));
        fdInclFilename = new FormData();
        fdInclFilename.left = new FormAttachment(middle, 0);
        fdInclFilename.top = new FormAttachment(wXmlConf, 4 * margin);
        wInclFilename.setLayoutData(fdInclFilename);

        wlInclFilenameField = new Label(wAdditionalFields, SWT.LEFT);
        wlInclFilenameField.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".InclFilenameField.Label"));
        props.setLook(wlInclFilenameField);
        fdlInclFilenameField = new FormData();
        fdlInclFilenameField.left = new FormAttachment(wInclFilename, margin);
        fdlInclFilenameField.top = new FormAttachment(wLimit, 4 * margin);
        wlInclFilenameField.setLayoutData(fdlInclFilenameField);
        wInclFilenameField = new TextVar(transMeta, wAdditionalFields, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wInclFilenameField);
        wInclFilenameField.addModifyListener(lsMod);
        fdInclFilenameField = new FormData();
        fdInclFilenameField.left = new FormAttachment(wlInclFilenameField, margin);
        fdInclFilenameField.top = new FormAttachment(wLimit, 4 * margin);
        fdInclFilenameField.right = new FormAttachment(100, 0);
        wInclFilenameField.setLayoutData(fdInclFilenameField);

        wlInclRownum = new Label(wAdditionalFields, SWT.RIGHT);
        wlInclRownum.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".InclRownum.Label"));
        props.setLook(wlInclRownum);
        fdlInclRownum = new FormData();
        fdlInclRownum.left = new FormAttachment(0, 0);
        fdlInclRownum.top = new FormAttachment(wInclFilenameField, margin);
        fdlInclRownum.right = new FormAttachment(middle, -margin);
        wlInclRownum.setLayoutData(fdlInclRownum);
        wInclRownum = new Button(wAdditionalFields, SWT.CHECK);
        props.setLook(wInclRownum);
        wInclRownum.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".InclRownum.Tooltip"));
        fdRownum = new FormData();
        fdRownum.left = new FormAttachment(middle, 0);
        fdRownum.top = new FormAttachment(wInclFilenameField, margin);
        wInclRownum.setLayoutData(fdRownum);

        wlInclRownumField = new Label(wAdditionalFields, SWT.RIGHT);
        wlInclRownumField.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".InclRownumField.Label"));
        props.setLook(wlInclRownumField);
        fdlInclRownumField = new FormData();
        fdlInclRownumField.left = new FormAttachment(wInclRownum, margin);
        fdlInclRownumField.top = new FormAttachment(wInclFilenameField, margin);
        wlInclRownumField.setLayoutData(fdlInclRownumField);
        wInclRownumField = new TextVar(transMeta, wAdditionalFields, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wInclRownumField);
        wInclRownumField.addModifyListener(lsMod);
        fdInclRownumField = new FormData();
        fdInclRownumField.left = new FormAttachment(wlInclRownumField, margin);
        fdInclRownumField.top = new FormAttachment(wInclFilenameField, margin);
        fdInclRownumField.right = new FormAttachment(100, 0);
        wInclRownumField.setLayoutData(fdInclRownumField);

        fdAdditionalFields = new FormData();
        fdAdditionalFields.left = new FormAttachment(0, margin);
        fdAdditionalFields.top = new FormAttachment(wXmlConf, margin);
        fdAdditionalFields.right = new FormAttachment(100, -margin);
        wAdditionalFields.setLayoutData(fdAdditionalFields);
    }

    private void genXmlConfFieldGroup() {
        startOfXMLConfFieldGroup();

        // Set Namespace aware ?
        setNamespaceAware();

        // Ignore comments ?
        ingoreComments();

        // Validate XML?
        validateXML();

        // use Token ?
        useToken();

        // Ignore Empty File
        ignoreEmptyFile();

        // do not fail if no files?
        doNotFailIfNoFiles();

        // Prune path to handle large files (streaming mode)
        streamingMode();
    }

    private void streamingMode() {
        wlPrunePath = new Label(wXmlConf, SWT.RIGHT);
        wlPrunePath.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".StreamingMode.Label"));
        props.setLook(wlPrunePath);
        fdlPrunePath = new FormData();
        fdlPrunePath.left = new FormAttachment(0, 0);
        fdlPrunePath.top = new FormAttachment(wLimit, margin);
        fdlPrunePath.right = new FormAttachment(middle, -margin);
        wlPrunePath.setLayoutData(fdlPrunePath);
        wPrunePath = new TextVar(transMeta, wXmlConf, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        wPrunePath.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".StreamingMode.Tooltip"));
        props.setLook(wPrunePath);
        wPrunePath.addModifyListener(lsMod);
        fdPrunePath = new FormData();
        fdPrunePath.left = new FormAttachment(middle, 0);
        fdPrunePath.top = new FormAttachment(wLimit, margin);
        fdPrunePath.right = new FormAttachment(100, 0);
        wPrunePath.setLayoutData(fdPrunePath);

        fdXmlConf = new FormData();
        fdXmlConf.left = new FormAttachment(0, margin);
        fdXmlConf.top = new FormAttachment(0, margin);
        fdXmlConf.right = new FormAttachment(100, -margin);
        wXmlConf.setLayoutData(fdXmlConf);
    }

    private void doNotFailIfNoFiles() {
        wldoNotFailIfNoFile = new Label(wXmlConf, SWT.RIGHT);
        wldoNotFailIfNoFile.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".doNotFailIfNoFile.Label"));
        props.setLook(wldoNotFailIfNoFile);
        fdldoNotFailIfNoFile = new FormData();
        fdldoNotFailIfNoFile.left = new FormAttachment(0, 0);
        fdldoNotFailIfNoFile.top = new FormAttachment(wIgnoreEmptyFile, margin);
        fdldoNotFailIfNoFile.right = new FormAttachment(middle, -margin);
        wldoNotFailIfNoFile.setLayoutData(fdldoNotFailIfNoFile);
        wdoNotFailIfNoFile = new Button(wXmlConf, SWT.CHECK);
        props.setLook(wdoNotFailIfNoFile);
        wdoNotFailIfNoFile.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".doNotFailIfNoFile.Tooltip"));
        fddoNotFailIfNoFile = new FormData();
        fddoNotFailIfNoFile.left = new FormAttachment(middle, 0);
        fddoNotFailIfNoFile.top = new FormAttachment(wIgnoreEmptyFile, margin);
        wdoNotFailIfNoFile.setLayoutData(fddoNotFailIfNoFile);

        wlLimit = new Label(wXmlConf, SWT.RIGHT);
        wlLimit.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Limit.Label"));
        props.setLook(wlLimit);
        fdlLimit = new FormData();
        fdlLimit.left = new FormAttachment(0, 0);
        fdlLimit.top = new FormAttachment(wdoNotFailIfNoFile, margin);
        fdlLimit.right = new FormAttachment(middle, -margin);
        wlLimit.setLayoutData(fdlLimit);
        wLimit = new Text(wXmlConf, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wLimit);
        wLimit.addModifyListener(lsMod);
        fdLimit = new FormData();
        fdLimit.left = new FormAttachment(middle, 0);
        fdLimit.top = new FormAttachment(wdoNotFailIfNoFile, margin);
        fdLimit.right = new FormAttachment(100, 0);
        wLimit.setLayoutData(fdLimit);
    }

    private void ignoreEmptyFile() {
        wlIgnoreEmptyFile = new Label(wXmlConf, SWT.RIGHT);
        wlIgnoreEmptyFile.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".IgnoreEmptyFile.Label"));
        props.setLook(wlIgnoreEmptyFile);
        fdlIgnoreEmptyFile = new FormData();
        fdlIgnoreEmptyFile.left = new FormAttachment(0, 0);
        fdlIgnoreEmptyFile.top = new FormAttachment(wuseToken, margin);
        fdlIgnoreEmptyFile.right = new FormAttachment(middle, -margin);
        wlIgnoreEmptyFile.setLayoutData(fdlIgnoreEmptyFile);
        wIgnoreEmptyFile = new Button(wXmlConf, SWT.CHECK);
        props.setLook(wIgnoreEmptyFile);
        wIgnoreEmptyFile.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".IgnoreEmptyFile.Tooltip"));
        fdIgnoreEmptyFile = new FormData();
        fdIgnoreEmptyFile.left = new FormAttachment(middle, 0);
        fdIgnoreEmptyFile.top = new FormAttachment(wuseToken, margin);
        wIgnoreEmptyFile.setLayoutData(fdIgnoreEmptyFile);
    }

    private void useToken() {
        wluseToken = new Label(wXmlConf, SWT.RIGHT);
        wluseToken.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".useToken.Label"));
        props.setLook(wluseToken);
        fdluseToken = new FormData();
        fdluseToken.left = new FormAttachment(0, 0);
        fdluseToken.top = new FormAttachment(wValidating, margin);
        fdluseToken.right = new FormAttachment(middle, -margin);
        wluseToken.setLayoutData(fdluseToken);
        wuseToken = new Button(wXmlConf, SWT.CHECK);
        props.setLook(wuseToken);
        wuseToken.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".useToken.Tooltip"));
        fduseToken = new FormData();
        fduseToken.left = new FormAttachment(middle, 0);
        fduseToken.top = new FormAttachment(wValidating, margin);
        wuseToken.setLayoutData(fduseToken);
    }

    private void validateXML() {
        wlValidating = new Label(wXmlConf, SWT.RIGHT);
        wlValidating.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Validating.Label"));
        props.setLook(wlValidating);
        fdlValidating = new FormData();
        fdlValidating.left = new FormAttachment(0, 0);
        fdlValidating.top = new FormAttachment(wIgnoreComment, margin);
        fdlValidating.right = new FormAttachment(middle, -margin);
        wlValidating.setLayoutData(fdlValidating);
        wValidating = new Button(wXmlConf, SWT.CHECK);
        props.setLook(wValidating);
        wValidating.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Validating.Tooltip"));
        fdValidating = new FormData();
        fdValidating.left = new FormAttachment(middle, 0);
        fdValidating.top = new FormAttachment(wIgnoreComment, margin);
        wValidating.setLayoutData(fdValidating);
    }

    private void ingoreComments() {
        wlIgnoreComment = new Label(wXmlConf, SWT.RIGHT);
        wlIgnoreComment.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".IgnoreComment.Label"));
        props.setLook(wlIgnoreComment);
        fdlIgnoreComment = new FormData();
        fdlIgnoreComment.left = new FormAttachment(0, 0);
        fdlIgnoreComment.top = new FormAttachment(wNameSpaceAware, margin);
        fdlIgnoreComment.right = new FormAttachment(middle, -margin);
        wlIgnoreComment.setLayoutData(fdlIgnoreComment);
        wIgnoreComment = new Button(wXmlConf, SWT.CHECK);
        props.setLook(wIgnoreComment);
        wIgnoreComment.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".IgnoreComment.Tooltip"));
        fdIgnoreComment = new FormData();
        fdIgnoreComment.left = new FormAttachment(middle, 0);
        fdIgnoreComment.top = new FormAttachment(wNameSpaceAware, margin);
        wIgnoreComment.setLayoutData(fdIgnoreComment);
    }

    private void setNamespaceAware() {
        wlNameSpaceAware = new Label(wXmlConf, SWT.RIGHT);
        wlNameSpaceAware.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".NameSpaceAware.Label"));
        props.setLook(wlNameSpaceAware);
        fdlNameSpaceAware = new FormData();
        fdlNameSpaceAware.left = new FormAttachment(0, 0);
        fdlNameSpaceAware.top = new FormAttachment(wEncoding, margin);
        fdlNameSpaceAware.right = new FormAttachment(middle, -margin);
        wlNameSpaceAware.setLayoutData(fdlNameSpaceAware);
        wNameSpaceAware = new Button(wXmlConf, SWT.CHECK);
        props.setLook(wNameSpaceAware);
        wNameSpaceAware.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".NameSpaceAware.Tooltip"));
        fdNameSpaceAware = new FormData();
        fdNameSpaceAware.left = new FormAttachment(middle, 0);
        fdNameSpaceAware.top = new FormAttachment(wEncoding, margin);
        wNameSpaceAware.setLayoutData(fdNameSpaceAware);
    }

    private void startOfXMLConfFieldGroup() {
        wXmlConf = new Group(wContentComp, SWT.SHADOW_NONE);
        props.setLook(wXmlConf);
        wXmlConf.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".wXmlConf.Label"));

        final FormLayout XmlConfgroupLayout = new FormLayout();
        XmlConfgroupLayout.marginWidth = 10;
        XmlConfgroupLayout.marginHeight = 10;
        wXmlConf.setLayout(XmlConfgroupLayout);

        wbbLoopPathList = new Button(wXmlConf, SWT.PUSH | SWT.CENTER);
        props.setLook(wbbLoopPathList);
        wbbLoopPathList.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".LoopPathList.Button"));
        wbbLoopPathList.setToolTipText(BaseMessages.getString(PKG, "System.Tooltip.BrowseForFileOrDirAndAdd"));
        fdbLoopPathList = new FormData();
        fdbLoopPathList.right = new FormAttachment(100, 0);
        fdbLoopPathList.top = new FormAttachment(0, 0);
        wbbLoopPathList.setLayoutData(fdbLoopPathList);

        // wbbLoopPathList.addSelectionListener(new SelectionAdapter() {
        //     public void widgetSelected(final SelectionEvent e) {
        //         getLoopPathList();
        //     }
        // });

        wlLoopXPath = new Label(wXmlConf, SWT.RIGHT);
        wlLoopXPath.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".LoopXPath.Label"));
        props.setLook(wlLoopXPath);
        fdlLoopXPath = new FormData();
        fdlLoopXPath.left = new FormAttachment(0, 0);
        fdlLoopXPath.top = new FormAttachment(0, margin);
        fdlLoopXPath.right = new FormAttachment(middle, -margin);
        wlLoopXPath.setLayoutData(fdlLoopXPath);
        wLoopXPath = new TextVar(transMeta, wXmlConf, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        wLoopXPath.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".LoopXPath.Tooltip"));
        props.setLook(wLoopXPath);
        wLoopXPath.addModifyListener(lsMod);
        fdLoopXPath = new FormData();
        fdLoopXPath.left = new FormAttachment(middle, 0);
        fdLoopXPath.top = new FormAttachment(0, margin);
        fdLoopXPath.right = new FormAttachment(wbbLoopPathList, -margin);
        wLoopXPath.setLayoutData(fdLoopXPath);

        wlEncoding = new Label(wXmlConf, SWT.RIGHT);
        wlEncoding.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Encoding.Label"));
        props.setLook(wlEncoding);
        fdlEncoding = new FormData();
        fdlEncoding.left = new FormAttachment(0, 0);
        fdlEncoding.top = new FormAttachment(wLoopXPath, margin);
        fdlEncoding.right = new FormAttachment(middle, -margin);
        wlEncoding.setLayoutData(fdlEncoding);
        wEncoding = new CCombo(wXmlConf, SWT.BORDER | SWT.READ_ONLY);
        wEncoding.setEditable(true);
        props.setLook(wEncoding);
        wEncoding.addModifyListener(lsMod);
        fdEncoding = new FormData();
        fdEncoding.left = new FormAttachment(middle, 0);
        fdEncoding.top = new FormAttachment(wLoopXPath, margin);
        fdEncoding.right = new FormAttachment(100, 0);
        wEncoding.setLayoutData(fdEncoding);
        wEncoding.addFocusListener(new FocusListener() {
            public void focusLost(final org.eclipse.swt.events.FocusEvent e) {
            }

            public void focusGained(final org.eclipse.swt.events.FocusEvent e) {
                final Cursor busy = new Cursor(shell.getDisplay(), SWT.CURSOR_WAIT);
                shell.setCursor(busy);
                setEncodings();
                shell.setCursor(null);
                busy.dispose();
            }
        });
    }

    private void startContentTab() {
        wContentTab = new CTabItem(wTabFolder, SWT.NONE);
        wContentTab.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Content.Tab"));

        final FormLayout contentLayout = new FormLayout();
        contentLayout.marginWidth = 3;
        contentLayout.marginHeight = 3;

        wContentComp = new Composite(wTabFolder, SWT.NONE);
        props.setLook(wContentComp);
        wContentComp.setLayout(contentLayout);
    }

    private void genFileTab() {
        startOfFileTab();
        // ///////////////////////////////
        // START OF Output Field GROUP //
        // ///////////////////////////////

        processOutputFieldGroup();

        // ///////////////////////////////////////////////////////////
        // / END OF Output Field GROUP
        // ///////////////////////////////////////////////////////////

        // Filename line
        genFilenameLine();

        // Filename list line
        genFilenameListLine();

        // Buttons to the right of the screen...
        genFileTabButtons();
    }

    private void genFilenameListLine() {
        wlFilenameList = new Label(wFileComp, SWT.RIGHT);
        wlFilenameList.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilenameList.Label"));
        props.setLook(wlFilenameList);
        fdlFilenameList = new FormData();
        fdlFilenameList.left = new FormAttachment(0, 0);
        fdlFilenameList.top = new FormAttachment(wExcludeFilemask, margin);
        fdlFilenameList.right = new FormAttachment(middle, -margin);
        wlFilenameList.setLayoutData(fdlFilenameList);
    }

    private void genFilenameLine() {
        wlFilename = new Label(wFileComp, SWT.RIGHT);
        wlFilename.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Filename.Label"));
        props.setLook(wlFilename);
        fdlFilename = new FormData();
        fdlFilename.left = new FormAttachment(0, 0);
        fdlFilename.top = new FormAttachment(wOutputField, margin);
        fdlFilename.right = new FormAttachment(middle, -margin);
        wlFilename.setLayoutData(fdlFilename);

        wbbFilename = new Button(wFileComp, SWT.PUSH | SWT.CENTER);
        props.setLook(wbbFilename);
        wbbFilename.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilenameBrowse.Button"));
        wbbFilename.setToolTipText(BaseMessages.getString(PKG, "System.Tooltip.BrowseForFileOrDirAndAdd"));
        fdbFilename = new FormData();
        fdbFilename.right = new FormAttachment(100, 0);
        fdbFilename.top = new FormAttachment(wOutputField, margin);
        wbbFilename.setLayoutData(fdbFilename);

        wbaFilename = new Button(wFileComp, SWT.PUSH | SWT.CENTER);
        props.setLook(wbaFilename);
        wbaFilename.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilenameAdd.Button"));
        wbaFilename.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilenameAdd.Tooltip"));
        fdbaFilename = new FormData();
        fdbaFilename.right = new FormAttachment(wbbFilename, -margin);
        fdbaFilename.top = new FormAttachment(wOutputField, margin);
        wbaFilename.setLayoutData(fdbaFilename);

        wFilename = new TextVar(transMeta, wFileComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wFilename);
        wFilename.addModifyListener(lsMod);
        fdFilename = new FormData();
        fdFilename.left = new FormAttachment(middle, 0);
        fdFilename.right = new FormAttachment(wbaFilename, -margin);
        fdFilename.top = new FormAttachment(wOutputField, margin);
        wFilename.setLayoutData(fdFilename);

        wlFilemask = new Label(wFileComp, SWT.RIGHT);
        wlFilemask.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".RegExp.Label"));
        props.setLook(wlFilemask);
        fdlFilemask = new FormData();
        fdlFilemask.left = new FormAttachment(0, 0);
        fdlFilemask.top = new FormAttachment(wFilename, margin);
        fdlFilemask.right = new FormAttachment(middle, -margin);
        wlFilemask.setLayoutData(fdlFilemask);
        wFilemask = new TextVar(transMeta, wFileComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wFilemask);
        wFilemask.addModifyListener(lsMod);
        fdFilemask = new FormData();
        fdFilemask.left = new FormAttachment(middle, 0);
        fdFilemask.top = new FormAttachment(wFilename, margin);
        fdFilemask.right = new FormAttachment(100, 0);
        wFilemask.setLayoutData(fdFilemask);

        wlExcludeFilemask = new Label(wFileComp, SWT.RIGHT);
        wlExcludeFilemask.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".ExcludeFilemask.Label"));
        props.setLook(wlExcludeFilemask);
        fdlExcludeFilemask = new FormData();
        fdlExcludeFilemask.left = new FormAttachment(0, 0);
        fdlExcludeFilemask.top = new FormAttachment(wFilemask, margin);
        fdlExcludeFilemask.right = new FormAttachment(middle, -margin);
        wlExcludeFilemask.setLayoutData(fdlExcludeFilemask);
        wExcludeFilemask = new TextVar(transMeta, wFileComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wExcludeFilemask);
        wExcludeFilemask.addModifyListener(lsMod);
        fdExcludeFilemask = new FormData();
        fdExcludeFilemask.left = new FormAttachment(middle, 0);
        fdExcludeFilemask.top = new FormAttachment(wFilemask, margin);
        fdExcludeFilemask.right = new FormAttachment(wFilename, 0, SWT.RIGHT);
        wExcludeFilemask.setLayoutData(fdExcludeFilemask);
    }

    private void processOutputFieldGroup() {
        startOfOuputFieldGroup();

        // Is XML string defined in a Field
        isXMLInField();

        // Is XML source is a file?
        isXMLSourceIsFile();

        // If XML string defined in a Field
        processXMLInField();
    }

    private void processXMLInField() {
        wlXMLField = new Label(wOutputField, SWT.RIGHT);
        wlXMLField.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".wlXMLField.Label"));
        props.setLook(wlXMLField);
        fdlXMLField = new FormData();
        fdlXMLField.left = new FormAttachment(0, -margin);
        fdlXMLField.top = new FormAttachment(wreadUrl, margin);
        fdlXMLField.right = new FormAttachment(middle, -2 * margin);
        wlXMLField.setLayoutData(fdlXMLField);

        wXMLField = new CCombo(wOutputField, SWT.BORDER | SWT.READ_ONLY);
        wXMLField.setEditable(true);
        props.setLook(wXMLField);
        wXMLField.addModifyListener(lsMod);
        fdXMLField = new FormData();
        fdXMLField.left = new FormAttachment(middle, -margin);
        fdXMLField.top = new FormAttachment(wreadUrl, margin);
        fdXMLField.right = new FormAttachment(100, -margin);
        wXMLField.setLayoutData(fdXMLField);
        wXMLField.addFocusListener(new FocusListener() {
            public void focusLost(final org.eclipse.swt.events.FocusEvent e) {
            }

            public void focusGained(final org.eclipse.swt.events.FocusEvent e) {
                final Cursor busy = new Cursor(shell.getDisplay(), SWT.CURSOR_WAIT);
                shell.setCursor(busy);
                setXMLStreamField();
                shell.setCursor(null);
                busy.dispose();
            }
        });

        fdOutputField = new FormData();
        fdOutputField.left = new FormAttachment(0, margin);
        fdOutputField.top = new FormAttachment(wFilenameList, margin);
        fdOutputField.right = new FormAttachment(100, -margin);
        wOutputField.setLayoutData(fdOutputField);
    }

    private void isXMLSourceIsFile() {
        wlXMLIsAFile = new Label(wOutputField, SWT.RIGHT);
        wlXMLIsAFile.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".XMLIsAFile.Label"));
        props.setLook(wlXMLIsAFile);
        fdlXMLIsAFile = new FormData();
        fdlXMLIsAFile.left = new FormAttachment(0, -margin);
        fdlXMLIsAFile.top = new FormAttachment(wXMLStreamField, margin);
        fdlXMLIsAFile.right = new FormAttachment(middle, -2 * margin);
        wlXMLIsAFile.setLayoutData(fdlXMLIsAFile);

        wXMLIsAFile = new Button(wOutputField, SWT.CHECK);
        props.setLook(wXMLIsAFile);
        wXMLIsAFile.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".XMLIsAFile.Tooltip"));
        fdXMLIsAFile = new FormData();
        fdXMLIsAFile.left = new FormAttachment(middle, -margin);
        fdXMLIsAFile.top = new FormAttachment(wXMLStreamField, margin);
        wXMLIsAFile.setLayoutData(fdXMLIsAFile);
        final SelectionAdapter lsxmlisafile = new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent arg0) {
                XMLSource = null;
                if (wXMLIsAFile.getSelection()) {
                    wreadUrl.setSelection(false);
                }
                input.setChanged();
            }
        };
        wXMLIsAFile.addSelectionListener(lsxmlisafile);

        // read url as source ?
        wlreadUrl = new Label(wOutputField, SWT.RIGHT);
        wlreadUrl.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".readUrl.Label"));
        props.setLook(wlreadUrl);
        fdlreadUrl = new FormData();
        fdlreadUrl.left = new FormAttachment(0, -margin);
        fdlreadUrl.top = new FormAttachment(wXMLIsAFile, margin);
        fdlreadUrl.right = new FormAttachment(middle, -2 * margin);
        wlreadUrl.setLayoutData(fdlreadUrl);
        wreadUrl = new Button(wOutputField, SWT.CHECK);
        props.setLook(wreadUrl);
        wreadUrl.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".readUrl.Tooltip"));
        fdreadUrl = new FormData();
        fdreadUrl.left = new FormAttachment(middle, -margin);
        fdreadUrl.top = new FormAttachment(wXMLIsAFile, margin);
        wreadUrl.setLayoutData(fdreadUrl);
        final SelectionAdapter lsreadurl = new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent arg0) {
                XMLSource = null;
                if (wreadUrl.getSelection()) {
                    wXMLIsAFile.setSelection(false);
                }
                input.setChanged();
            }
        };
        wreadUrl.addSelectionListener(lsreadurl);
    }

    private void isXMLInField() {
        wlXmlStreamField = new Label(wOutputField, SWT.RIGHT);
        wlXmlStreamField.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".wlXmlStreamField.Label"));
        props.setLook(wlXmlStreamField);
        fdlXMLStreamField = new FormData();
        fdlXMLStreamField.left = new FormAttachment(0, -margin);
        fdlXMLStreamField.top = new FormAttachment(0, margin);
        fdlXMLStreamField.right = new FormAttachment(middle, -2 * margin);
        wlXmlStreamField.setLayoutData(fdlXMLStreamField);

        wXMLStreamField = new Button(wOutputField, SWT.CHECK);
        props.setLook(wXMLStreamField);
        wXMLStreamField.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".wXmlStreamField.Tooltip"));
        fdXSDFileField = new FormData();
        fdXSDFileField.left = new FormAttachment(middle, -margin);
        fdXSDFileField.top = new FormAttachment(0, margin);
        wXMLStreamField.setLayoutData(fdXSDFileField);
        final SelectionAdapter lsxmlstream = new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent arg0) {
                XMLSource = null;
                ActiveXmlStreamField();
                input.setChanged();
            }
        };
        wXMLStreamField.addSelectionListener(lsxmlstream);
    }

    private void startOfOuputFieldGroup() {
        wOutputField = new Group(wFileComp, SWT.SHADOW_NONE);
        props.setLook(wOutputField);
        wOutputField.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".wOutputField.Label"));

        final FormLayout outputfieldgroupLayout = new FormLayout();
        outputfieldgroupLayout.marginWidth = 10;
        outputfieldgroupLayout.marginHeight = 10;
        wOutputField.setLayout(outputfieldgroupLayout);
    }

    private void startOfFileTab() {
        wFileTab = new CTabItem(wTabFolder, SWT.NONE);
        wFileTab.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".File.Tab"));

        wFileComp = new Composite(wTabFolder, SWT.NONE);
        props.setLook(wFileComp);

        final FormLayout fileLayout = new FormLayout();
        fileLayout.marginWidth = 3;
        fileLayout.marginHeight = 3;
        wFileComp.setLayout(fileLayout);
    }

    private Display initDialog() {
        final Shell parent = getParent();
        final Display display = parent.getDisplay();

        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX | SWT.MIN);
        props.setLook(shell);
        setShellImage(shell, input);

        lsMod = new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                input.setChanged();
            }
        };
        changed = input.hasChanged();

        final FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = Const.FORM_MARGIN;
        formLayout.marginHeight = Const.FORM_MARGIN;

        shell.setLayout(formLayout);
        shell.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".DialogTitle"));

        middle = props.getMiddlePct();
        margin = Const.MARGIN;
        return display;
    }

    private void genStepNameLine() {
        wlStepname = new Label(shell, SWT.RIGHT);
        wlStepname.setText(BaseMessages.getString(PKG, "System.Label.StepName"));
        props.setLook(wlStepname);
        fdlStepname = new FormData();
        fdlStepname.left = new FormAttachment(0, 0);
        fdlStepname.top = new FormAttachment(0, margin);
        fdlStepname.right = new FormAttachment(middle, -margin);
        wlStepname.setLayoutData(fdlStepname);
        wStepname = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        wStepname.setText(stepname);
        props.setLook(wStepname);
        wStepname.addModifyListener(lsMod);
        fdStepname = new FormData();
        fdStepname.left = new FormAttachment(middle, 0);
        fdStepname.top = new FormAttachment(0, margin);
        fdStepname.right = new FormAttachment(100, 0);
        wStepname.setLayoutData(fdStepname);

        wTabFolder = new CTabFolder(shell, SWT.BORDER);
        props.setLook(wTabFolder, Props.WIDGET_STYLE_TAB);
    }

    public void genFileTabButtons(){
        wbdFilename = new Button(wFileComp, SWT.PUSH | SWT.CENTER);
        props.setLook(wbdFilename);
        wbdFilename.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilenameRemove.Button"));
        wbdFilename.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilenameRemove.Tooltip"));
        fdbdFilename = new FormData();
        fdbdFilename.right = new FormAttachment(100, 0);
        fdbdFilename.top = new FormAttachment(wExcludeFilemask, 40);
        wbdFilename.setLayoutData(fdbdFilename);

        wbeFilename = new Button(wFileComp, SWT.PUSH | SWT.CENTER);
        props.setLook(wbeFilename);
        wbeFilename.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilenameEdit.Button"));
        wbeFilename.setToolTipText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilenameEdit.Tooltip"));
        fdbeFilename = new FormData();
        fdbeFilename.right = new FormAttachment(100, 0);
        fdbeFilename.left = new FormAttachment(wbdFilename, 0, SWT.LEFT);
        fdbeFilename.top = new FormAttachment(wbdFilename, margin);
        wbeFilename.setLayoutData(fdbeFilename);

        wbShowFiles = new Button(wFileComp, SWT.PUSH | SWT.CENTER);
        props.setLook(wbShowFiles);
        wbShowFiles.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".ShowFiles.Button"));
        fdbShowFiles = new FormData();
        fdbShowFiles.left = new FormAttachment(middle, 0);
        fdbShowFiles.bottom = new FormAttachment(100, 0);
        wbShowFiles.setLayoutData(fdbShowFiles);

        final ColumnInfo[] colinfo = new ColumnInfo[5];
        colinfo[0] = new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Files.Filename.Column"),
                ColumnInfo.COLUMN_TYPE_TEXT, false);
        colinfo[1] = new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Files.Wildcard.Column"),
                ColumnInfo.COLUMN_TYPE_TEXT, false);
        colinfo[2] = new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Files.ExcludeWildcard.Column"),
                ColumnInfo.COLUMN_TYPE_TEXT, false);

        colinfo[0].setUsingVariables(true);
        colinfo[1].setUsingVariables(true);
        colinfo[1].setToolTip(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Files.Wildcard.Tooltip"));
        colinfo[2].setUsingVariables(true);
        colinfo[2].setToolTip(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Files.ExcludeWildcard.Tooltip"));
        // colinfo[3] = new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Required.Column"),
        //         ColumnInfo.COLUMN_TYPE_CCOMBO, GetXMLDataMeta.RequiredFilesDesc);
        // colinfo[3].setToolTip(BaseMessages.getString(PKG, DEFAULT_PREFIX+".Required.Tooltip"));
        // colinfo[4] = new ColumnInfo(BaseMessages.getString(PKG, DEFAULT_PREFIX+".IncludeSubDirs.Column"),
        //         ColumnInfo.COLUMN_TYPE_CCOMBO, GetXMLDataMeta.RequiredFilesDesc);
        colinfo[4].setToolTip(BaseMessages.getString(PKG, DEFAULT_PREFIX+".IncludeSubDirs.Tooltip"));

        wFilenameList = new TableView(transMeta, wFileComp, SWT.FULL_SELECTION | SWT.SINGLE | SWT.BORDER, colinfo, 2,
                lsMod, props);
        props.setLook(wFilenameList);
        fdFilenameList = new FormData();
        fdFilenameList.left = new FormAttachment(middle, 0);
        fdFilenameList.right = new FormAttachment(wbdFilename, -margin);
        fdFilenameList.top = new FormAttachment(wExcludeFilemask, margin);
        fdFilenameList.bottom = new FormAttachment(wbShowFiles, -margin);
        wFilenameList.setLayoutData(fdFilenameList);

        fdFileComp = new FormData();
        fdFileComp.left = new FormAttachment(0, 0);
        fdFileComp.top = new FormAttachment(0, 0);
        fdFileComp.right = new FormAttachment(100, 0);
        fdFileComp.bottom = new FormAttachment(100, 0);
        wFileComp.setLayoutData(fdFileComp);

        wFileComp.layout();
        wFileTab.setControl(wFileComp);
    }

    private void setXMLStreamField() {
        try {

            wXMLField.removeAll();

            final RowMetaInterface r = transMeta.getPrevStepFields(stepname);
            if (r != null) {
                final String[] fieldNames = r.getFieldNames();
                if (fieldNames != null) {

                    for (int i = 0; i < fieldNames.length; i++) {
                        wXMLField.add(fieldNames[i]);
                    }
                }
            }
        } catch (final KettleException ke) {
            if (!Const.isOSX()) { // see PDI-8871 for details
                shell.setFocus();
            }
            wXMLField.add(EMPTY_FIELDS);
            wXMLField.setText(EMPTY_FIELDS);
            new ErrorDialog(shell, BaseMessages.getString(PKG, DEFAULT_PREFIX+".FailedToGetFields.DialogTitle"),
                    BaseMessages.getString(PKG, DEFAULT_PREFIX+".FailedToGetFields.DialogMessage"), ke);
        }
    }

    private void ActiveXmlStreamField() {
        wlXMLField.setEnabled(wXMLStreamField.getSelection());
        wXMLField.setEnabled(wXMLStreamField.getSelection());
        wlXMLIsAFile.setEnabled(wXMLStreamField.getSelection());
        wXMLIsAFile.setEnabled(wXMLStreamField.getSelection());
        wlreadUrl.setEnabled(wXMLStreamField.getSelection());
        wreadUrl.setEnabled(wXMLStreamField.getSelection());

        wlFilename.setEnabled(!wXMLStreamField.getSelection());
        wbbFilename.setEnabled(!wXMLStreamField.getSelection());
        wbaFilename.setEnabled(!wXMLStreamField.getSelection());
        wFilename.setEnabled(!wXMLStreamField.getSelection());
        wlExcludeFilemask.setEnabled(!wXMLStreamField.getSelection());
        wExcludeFilemask.setEnabled(!wXMLStreamField.getSelection());
        wlFilemask.setEnabled(!wXMLStreamField.getSelection());
        wFilemask.setEnabled(!wXMLStreamField.getSelection());
        wlFilenameList.setEnabled(!wXMLStreamField.getSelection());
        wbdFilename.setEnabled(!wXMLStreamField.getSelection());
        wbeFilename.setEnabled(!wXMLStreamField.getSelection());
        wbShowFiles.setEnabled(!wXMLStreamField.getSelection());
        wlFilenameList.setEnabled(!wXMLStreamField.getSelection());
        wFilenameList.setEnabled(!wXMLStreamField.getSelection());
        wInclFilename.setEnabled(!wXMLStreamField.getSelection());
        wlInclFilename.setEnabled(!wXMLStreamField.getSelection());

        if (wXMLStreamField.getSelection()) {
            wInclFilename.setSelection(false);
            wlInclFilenameField.setEnabled(false);
            wInclFilenameField.setEnabled(false);
        } else {
            wlInclFilenameField.setEnabled(wInclFilename.getSelection());
            wInclFilenameField.setEnabled(wInclFilename.getSelection());
        }

        if (wXMLStreamField.getSelection() && !wXMLIsAFile.getSelection()) {
            wEncoding.setEnabled(false);
            wlEncoding.setEnabled(false);
        } else {
            wEncoding.setEnabled(true);
            wlEncoding.setEnabled(true);
        }
        wAddResult.setEnabled(!wXMLStreamField.getSelection());
        wlAddResult.setEnabled(!wXMLStreamField.getSelection());
        wLimit.setEnabled(!wXMLStreamField.getSelection());
        wlLimit.setEnabled(!wXMLStreamField.getSelection());
        wPreview.setEnabled(!wXMLStreamField.getSelection());
        wPrunePath.setEnabled(!wXMLStreamField.getSelection());
        wlPrunePath.setEnabled(!wXMLStreamField.getSelection());
        wlShortFileFieldName.setEnabled(!wXMLStreamField.getSelection());
        wShortFileFieldName.setEnabled(!wXMLStreamField.getSelection());
        wlPathFieldName.setEnabled(!wXMLStreamField.getSelection());
        wPathFieldName.setEnabled(!wXMLStreamField.getSelection());
        wlIsHiddenName.setEnabled(!wXMLStreamField.getSelection());
        wIsHiddenName.setEnabled(!wXMLStreamField.getSelection());
        wlLastModificationTimeName.setEnabled(!wXMLStreamField.getSelection());
        wLastModificationTimeName.setEnabled(!wXMLStreamField.getSelection());
        wlUriName.setEnabled(!wXMLStreamField.getSelection());
        wUriName.setEnabled(!wXMLStreamField.getSelection());
        wlRootUriName.setEnabled(!wXMLStreamField.getSelection());
        wRootUriName.setEnabled(!wXMLStreamField.getSelection());
        wlExtensionFieldName.setEnabled(!wXMLStreamField.getSelection());
        wExtensionFieldName.setEnabled(!wXMLStreamField.getSelection());
        wlSizeFieldName.setEnabled(!wXMLStreamField.getSelection());
        wSizeFieldName.setEnabled(!wXMLStreamField.getSelection());
        if (wXMLStreamField.getSelection()) {
            wShortFileFieldName.setText("");
            wPathFieldName.setText("");
            wIsHiddenName.setText("");
            wLastModificationTimeName.setText("");
            wUriName.setText("");
            wRootUriName.setText("");
            wExtensionFieldName.setText("");
            wSizeFieldName.setText("");

        }

    }

    // private void getLoopPathList() {
    //     try {
    //         final GetXMLDataMeta meta = new GetXMLDataMeta();
    //         getInfo(meta);
    //         if (meta.isInFields()) {
    //             if (meta.isReadUrl()) {
    //                 // Read URL
    //                 String url = XMLSource;
    //                 if (url == null) {
    //                     final EnterStringDialog d = new EnterStringDialog(shell, "",
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".AskURL.Title"),
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".AskURL.Message"));
    //                     url = d.open();
    //                 }
    //                 populateLoopPaths(meta, url, true, true);

    //             } else if (meta.getIsAFile()) {
    //                 // Read file
    //                 String str = XMLSource;
    //                 if (str == null) {
    //                     final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
    //                     dialog.setFilterExtensions(new String[] { "*.xml;*.XML", "*" });
    //                     dialog.setFilterNames(new String[] { BaseMessages.getString(PKG, "System.FileType.XMLFiles"),
    //                             BaseMessages.getString(PKG, "System.FileType.AllFiles") });

    //                     if (dialog.open() != null) {
    //                         str = dialog.getFilterPath() + System.getProperty("file.separator") + dialog.getFileName();
    //                     }
    //                     populateLoopPaths(meta, str, false, false);
    //                 }
    //             } else {
    //                 // Read xml
    //                 String xml = XMLSource;
    //                 if (xml == null) {
    //                     final EnterTextDialog d = new EnterTextDialog(shell,
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".AskXML.Title"),
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".AskXML.Message"), null);
    //                     xml = d.open();
    //                 }
    //                 populateLoopPaths(meta, xml, true, false);
    //             }
    //         } else {

    //             final FileInputList fileinputList = meta.getFiles(transMeta);

    //             if (fileinputList.nrOfFiles() > 0) {
    //                 // Check the first file

    //                 if (fileinputList.getFile(0).exists()) {
    //                     populateLoopPaths(meta, KettleVFS.getFilename(fileinputList.getFile(0)), false, false);
    //                 } else {
    //                     // The file not exists !
    //                     throw new KettleException(
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".Exception.FileDoesNotExist",
    //                                     KettleVFS.getFilename(fileinputList.getFile(0))));
    //                 }
    //             } else {
    //                 // No file specified
    //                 final MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
    //                 mb.setMessage(BaseMessages.getString(PKG, DEFAULT_PREFIX+".FilesMissing.DialogMessage"));
    //                 mb.setText(BaseMessages.getString(PKG, "System.Dialog.Error.Title"));
    //                 mb.open();
    //             }
    //         }
    //     } catch (final Exception e) {
    //         new ErrorDialog(shell, BaseMessages.getString(PKG, DEFAULT_PREFIX+".UnableToGetListOfPaths.Title"),
    //                 BaseMessages.getString(PKG, DEFAULT_PREFIX+".UnableToGetListOfPaths.Message"), e);
    //     }
    // }

    // private void get() {
    //     final InputStream is = null;
    //     try {
    //         final GetXMLDataMeta meta = new GetXMLDataMeta();
    //         getInfo(meta);

    //         // check if the path is given
    //         if (!checkLoopXPath(meta)) {
    //             return;
    //         }
    //         int clearFields = SWT.YES;
    //         if (wFields.nrNonEmpty() > 0) {
    //             final MessageBox messageBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.CANCEL | SWT.ICON_QUESTION);
    //             messageBox.setMessage(BaseMessages.getString(PKG, DEFAULT_PREFIX+".ClearFieldList.DialogMessage"));
    //             messageBox.setText(BaseMessages.getString(PKG, DEFAULT_PREFIX+".ClearFieldList.DialogTitle"));
    //             clearFields = messageBox.open();
    //             if (clearFields == SWT.CANCEL) {
    //                 return;
    //             }
    //         }

    //         if (meta.isInFields()) {
    //             if (meta.isReadUrl()) {
    //                 // Read URL
    //                 String url = XMLSource;
    //                 if (url == null) {
    //                     final EnterStringDialog enterStringDialog = new EnterStringDialog(shell, "",
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".AskURL.Title"),
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".AskURL.Title"));
    //                     url = enterStringDialog.open();
    //                 }
    //                 populateFields(meta, url, true, true, clearFields);

    //             } else if (meta.getIsAFile()) {
    //                 // Read file
    //                 String str = XMLSource;
    //                 if (str == null) {
    //                     final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
    //                     dialog.setFilterExtensions(new String[] { "*.xml;*.XML", "*" });
    //                     dialog.setFilterNames(new String[] { BaseMessages.getString(PKG, "System.FileType.XMLFiles"),
    //                             BaseMessages.getString(PKG, "System.FileType.AllFiles") });

    //                     if (dialog.open() != null) {
    //                         str = dialog.getFilterPath() + System.getProperty("file.separator") + dialog.getFileName();
    //                     }
    //                 }
    //                 populateFields(meta, str, false, false, clearFields);
    //             } else {
    //                 // Read xml
    //                 String xml = XMLSource;
    //                 if (xml == null) {
    //                     final EnterTextDialog d = new EnterTextDialog(shell,
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".AskXML.Title"),
    //                             BaseMessages.getString(PKG, DEFAULT_PREFIX+".AskXML.Message"), null);
    //                     xml = d.open();
    //                 }
    //                 populateFields(meta, xml, true, false, clearFields);
    //             }
    //         } else {

    //             final FileInputList inputList = meta.getFiles(transMeta);

    //             if (inputList.getFiles().size() > 0) {
    //                 populateFields(meta, KettleVFS.getFilename(inputList.getFile(0)), false, false, clearFields);
    //             }
    //         }
    //     } catch (final Exception e) {
    //         new ErrorDialog(shell, BaseMessages.getString(PKG, DEFAULT_PREFIX+".ErrorParsingData.DialogTitle"),
    //                 BaseMessages.getString(PKG, DEFAULT_PREFIX+".ErrorParsingData.DialogMessage"), e);
    //     } finally {
    //         try {
    //             if (is != null) {
    //                 is.close();
    //             }
    //         } catch (final Exception e) { /* Ignore */
    //         }
    //     }
    // }
    private void setEncodings() {
        // Encoding of the text file:
        if ( !gotEncodings ) {
          gotEncodings = true;
    
          wEncoding.removeAll();
          ArrayList<Charset> values = new ArrayList<Charset>( Charset.availableCharsets().values() );
          for ( int i = 0; i < values.size(); i++ ) {
            Charset charSet = values.get( i );
            wEncoding.add( charSet.displayName() );
          }
    
          // Now select the default!
          String defEncoding = Const.getEnvironmentVariable( "file.encoding", "UTF-8" );
          int idx = Const.indexOfString( defEncoding, wEncoding.getItems() );
          if ( idx >= 0 ) {
            wEncoding.select( idx );
          }
        }
      }

}