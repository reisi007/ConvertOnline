CREATE SCHEMA  IF NOT EXISTS `converter` ;
CREATE TABLE IF NOT EXISTS converter.PossibleFileEndings (
  `component` VARCHAR(2) NOT NULL,
  `fileEnd` VARCHAR(10) NOT NULL,
  `import` TINYINT(1) NOT NULL,
  `export` TINYINT(1) NOT NULL,
  PRIMARY KEY (`component`, `fileEnd`)
);
truncate converter.possiblefileendings;
CREATE TABLE IF NOT EXISTS converter.MimeTypes (
  `fileEnd` VARCHAR(10) NOT NULL,
  `mimetype` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`fileEnd`)
);
truncate converter.MimeTypes;
INSERT INTO converter.MimeTypes (`fileEnd`, `mimetype`) VALUES
('123', 'application/lotus123'),
('abw', 'application/abiword'),
('bmp', 'image/bmp'),
('cdr', 'application/coreldraw'),
('cgm', 'image/cgm'),
('cmx', 'application/cmx'),
('csv', 'text/csv'),
('cwk', 'application/clarisworks'),
('dbf', 'application/dbase'),
('doc', 'application/msword'),
('docm', 'application/vnd.ms-word.document.macroEnabled.12'),
('docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'),
('dot', 'application/msword'),
('dotm', 'application/vnd.ms-word.template.macroEnabled.12'),
('dotx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.template'),
('dxf', 'image/vnd.dxf'),
('emf', 'application/emf'),
('eps', 'application/eps'),
('fh', 'image/x-freehand'),
('fh1', 'image/x-freehand'),
('fh10', 'image/x-freehand'),
('fh11', 'image/x-freehand'),
('fh2', 'image/x-freehand'),
('fh3', 'image/x-freehand'),
('fh4', 'image/x-freehand'),
('fh5', 'image/x-freehand'),
('fh6', 'image/x-freehand'),
('fh7', 'image/x-freehand'),
('fh8', 'image/x-freehand'),
('fh9', 'image/x-freehand'),
('fodg', 'text/xml'),
('fodp', 'text/xml'),
('fods', 'text/xml'),
('fodt', 'text/xml'),
('gif', 'image/gif'),
('htm', 'text/html'),
('html', 'text/html'),
('hwp', 'application/x-hwp'),
('jpeg', 'image/jpeg'),
('jpg', 'image/jpeg'),
('key', 'application/x-iwork-keynote-sffkey'),
('lwp', 'application/vnd.lotus-wordpro'),
('met', 'application/octet-stream'),
('mml', 'text/plain'),
('mov', 'image/mov'),
('odf', 'application/vnd.oasis.opendocument.formula'),
('odg', 'application/vnd.oasis.opendocument.graphics'),
('odp', 'application/vnd.oasis.opendocument.presentation'),
('ods', 'application/vnd.oasis.opendocument.spreadsheet'),
('odt', 'application/vnd.oasis.opendocument.text'),
('otg', 'application/vnd.oasis.opendocument.graphics-template'),
('otp', 'application/vnd.oasis.opendocument.presentation-template'),
('ots', 'application/vnd.oasis.opendocument.spreadsheet-template'),
('ott', 'application/vnd.oasis.opendocument.text-template'),
('pbm', 'image/x-portable-bitmap'),
('pcd', 'image/x-photo-cd'),
('pct', 'image/x-pict'),
('pcx', 'application/pcx'),
('pdf', 'application/pdf'),
('pgm', 'image/x-portable-graymap'),
('pict', 'image/pict'),
('png', 'image/png'),
('pot', 'application/vnd.ms-powerpoint'),
('potm', 'application/vnd.ms-powerpoint.template.macroEnabled.12'),
('potx', 'application/vnd.openxmlformats-officedocument.presentationml.template'),
('ppm', 'application/ppm'),
('pps', 'application/vnd.ms-powerpoint'),
('ppsx', 'application/vnd.openxmlformats-officedocument.presentationml.slideshow'),
('ppt', 'application/vnd.ms-powerpoint'),
('pptx', 'application/vnd.openxmlformats-officedocument.presentationml.presentation'),
('psd', 'image/photoshop'),
('pub', 'application/x-mspublisher'),
('ras', 'application/ras'),
('rtf', 'application/rtf'),
('sgf', 'image/x-sgf'),
('sgv', 'image/x-sgv'),
('slk', 'application/vnd.ms-excel'),
('stc', 'application/vnd.sun.xml.calc.template'),
('std', 'application/vnd.sun.xml.draw.template'),
('sti', 'application/vnd.sun.xml.impress.template'),
('svg', 'image/svg'),
('svgz', 'image/svg'),
('svm', 'image/x-svm'),
('sxc', 'application/vnd.sun.xml.calc'),
('sxd', 'application/vnd.sun.xml.draw'),
('sxi', 'application/vnd.sun.xml.impress'),
('sxm', 'application/vnd.sun.xml.math'),
('sxw', 'application/vnd.sun.xml.writer'),
('tga', 'application/tga'),
('tif', 'image/tif'),
('tiff', 'image/tiff'),
('txt', 'text/plain'),
('uof', 'application/octet-stream'),
('uop', 'application/octet-stream'),
('uos', 'application/octet-stream'),
('vdx', 'application/visio'),
('vsd', 'application/visio'),
('vsdm', 'application/visio'),
('vsdx', 'application/visio'),
('wb2', 'application/lotus123'),
('wdb', 'application/vnd.ms-works'),
('wks', 'application/lotus123'),
('wmf', 'application/wmf'),
('wpd', 'application/vnd.wordperfect'),
('wpg', 'application/wpg'),
('wps', 'application/vnd.ms-works'),
('xbm', 'image/x-xbitmap'),
('xlc', 'application/vnd.ms-excel'),
('xlk', 'application/vnd.ms-excel'),
('xlm', 'application/vnd.ms-excel'),
('xls', 'application/vnd.ms-excel'),
('xlt', 'application/vnd.ms-excel'),
('xlw', 'application/vnd.ms-excel'),
('xpm', 'image/xpm'),
('xslb', 'application/vnd.ms-excel.sheet.binary.macroEnabled.12'),
('xslm', 'application/vnd.ms-excel.addin.macroEnabled.12'),
('xslx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'),
('zabw', 'application/abiword-compressed');
