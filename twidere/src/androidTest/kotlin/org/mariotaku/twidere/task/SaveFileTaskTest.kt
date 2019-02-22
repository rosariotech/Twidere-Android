package org.mariotaku.twidere.task

import org.junit.Assert.assertEquals
import org.junit.Test

class SaveFileTaskTest{
    @Test
    @Throws(Exception::class)
    fun testGetFileNameWithExtension(){
        assertEquals("pbs_twimg_com_media_abcdefghijklmn.jpg",
            SaveFileTask.getFileNameWithExtension("pbs_twimg_com_media_abcdefghijklmn.jpg",
                "jpg", '_', null))
        assertEquals("pbs_twimg_com_media_abcdefghijklmn.jpg",
            SaveFileTask.getFileNameWithExtension("pbs_twimg_com_media_abcdefghijklmn.jpg",
                null, '_', null))
        assertEquals("pbs_twimg_com_media_abcdefghijklmn.jpg",
            SaveFileTask.getFileNameWithExtension("pbs_twimg_com_media_abcdefghijklmn.jpg",
                null, '_', "suffix"))
    }
}