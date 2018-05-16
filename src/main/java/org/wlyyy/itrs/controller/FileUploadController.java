package org.wlyyy.itrs.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.exception.StorageFileNotFoundException;
import org.wlyyy.itrs.service.StorageService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private static final Set<String> FILE_EXTENSIONS = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "doc", "docx", "zip", "rar", "gz", "tar"));
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("")
    public List<String> listUploadedFiles(Model model) throws IOException {

        return storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList());
    }

    @GetMapping("/files/{filename:.+}/{aliasFileName}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename, @PathVariable("aliasFileName") String aliasFileName) throws UnsupportedEncodingException {

        final String extension = FilenameUtils.getExtension(filename);
        Resource file = storageService.loadAsResource(filename);
        // 防止文件名乱码
        final String encode = URLEncoder.encode(aliasFileName, "UTF-8");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + encode + "." + extension + "\"").body(file);
    }

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public Map<String, Object> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                RedirectAttributes redirectAttributes) {
        final Map<String, Object> response = new HashMap<>(2);
        if (!checkFileExtension(file.getName())) {
            response.put("message", St.r("不支持的扩展名 {}.", FilenameUtils.getExtension(file.getName())));
            return response;
        }
        final String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
        // 在数据库中的存储形式为： 简历?UUID.docx
        final String fileName = baseName + "?" + storageService.store(file);
        response.put("message", St.r("Success upload {}.", file.getOriginalFilename()));
        response.put("fileName", fileName);
        return response;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    private boolean checkFileExtension(String fileName) {
//        final String extension = FilenameUtils.getExtension(fileName);
//        return FILE_EXTENSIONS.contains(fileName);
        return true;
    }

}